package com.airline.booking.application.ticket.service.impl;

import com.airline.booking.application.payment.dataTransferObject.request.InitPaymentRequest;
import com.airline.booking.application.payment.dataTransferObject.response.InitPaymentResponse;
import com.airline.booking.application.payment.service.IPaymentGatewayService;
import com.airline.booking.application.ticket.dataTransferObject.request.ApplyDiscountRequest;
import com.airline.booking.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.airline.booking.application.ticket.dataTransferObject.request.TicketConfirmRequest;
import com.airline.booking.application.ticket.dataTransferObject.response.TicketBookingResponse;
import com.airline.booking.application.ticket.dataTransferObject.response.TicketConfirmResponse;
import com.airline.booking.application.ticket.dataTransferObject.response.TicketResponse;
import com.airline.booking.application.ticket.mapper.TicketMapper;
import com.airline.booking.application.ticket.service.ITicketUseCase;
import com.airline.booking.infrastructure.exception.ApplicationException;
import com.airline.booking.domain.flight.model.Flight;
import com.airline.booking.domain.flight.service.IFlightPriceService;
import com.airline.booking.domain.flight.service.IFlightService;
import com.airline.booking.domain.ticket.model.Ticket;
import com.airline.booking.domain.ticket.service.ITicketService;
import com.airline.booking.application.component.KafkaProducer;
import com.airline.booking.infrastructure.dataTransferObject.request.EmailRequest;
import com.airline.booking.infrastructure.service.cache.CacheService;
import com.airline.booking.infrastructure.util.Template;
import jakarta.persistence.OptimisticLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TicketUseCaseImpl implements ITicketUseCase {
    private static final Logger logger = LoggerFactory.getLogger(TicketUseCaseImpl.class);

    private final ITicketService ticketService;
    private final IFlightService flightService;
    private final IFlightPriceService flightPriceService;
    private final TicketMapper ticketMapper;
    private final IPaymentGatewayService paymentGatewayService;
    private final KafkaProducer kafkaProducer;
    private final CacheService cacheService;

    @Value("${spring.kafka.topic.email}")
    private String topicEmail;


    @Value("${spring.data.redis.key.ticket.ticket-confirm.value}")
    private String confirmKey;
    @Value("${spring.data.redis.key.ticket.ticket-confirm.timout}")
    private long confirmTimeout;

    private static final int confirmMaxRetries = 5;
    private static final long confirmRetryDelay = 1000L;

    @Value("${spring.data.redis.key.ticket.ticket-payment.value}")
    private String paymentKey;


    public TicketUseCaseImpl(ITicketService ticketService,
                             IFlightService flightService,
                             IFlightPriceService flightPriceService,
                             TicketMapper ticketMapper,
                             IPaymentGatewayService paymentGatewayService,
                             KafkaProducer kafkaProducer,
                             CacheService cacheService) {
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.flightPriceService = flightPriceService;
        this.ticketMapper = ticketMapper;
        this.paymentGatewayService = paymentGatewayService;
        this.kafkaProducer = kafkaProducer;
        this.cacheService = cacheService;
    }

    @Override
    public TicketBookingResponse booking(TicketBookingRequest request) {
        String flightId = request.getFlightId();
        Flight existFlight = flightService.getById(request.getFlightId());

        var totalPare = flightPriceService.calculateTotalFare(
                existFlight,
                request.getChildSeats(), request.getAdultSeats()
        );
        Ticket ticket = new Ticket(
                null,
                flightId,
                totalPare,
                request.getChildSeats(), request.getAdultSeats(),
                null,
                null,
                null,
                null
        );
        Ticket newTicket = ticketService.create(ticket);
        String key = String.format(confirmKey, newTicket.getId());
        cacheService.put(key, newTicket, confirmTimeout);

        logger.info("Ticket creating successful. Ticket ID: {}", newTicket.getId());

        TicketResponse ticketResponse = ticketMapper.toResponse(newTicket);
        return new TicketBookingResponse(
                ticketResponse,
                null
        );
    }

    @Override
    public TicketConfirmResponse confirm(
            String ticketId,
            TicketConfirmRequest request
    ) {
        String cacheKey = String.format(confirmKey, ticketId);

        if (!cacheService.exists(cacheKey)) {
            throw new ApplicationException("Ticket has not been booked!");
        }

        Ticket existTicket = cacheService.get(cacheKey, Ticket.class);
        existTicket.setContactEmail(request.getContactEmail());
        logger.info("Ticket booked. Ticket: {}", existTicket);

        Flight flight = confirmTicketWithRetry(existTicket, cacheKey);

        InitPaymentRequest initPaymentRequest = new InitPaymentRequest(
                request.getIpAddress(),
                request.getContactEmail(),
                ticketId,
                existTicket.getTotalPrice()
        );
        InitPaymentResponse initPaymentResponse = paymentGatewayService.init(initPaymentRequest);

        existTicket.setPaymentId(initPaymentResponse.getPaymentId());
        Ticket confirmedTicket = ticketService.confirm(existTicket);

        cacheService.put(String.format(paymentKey, existTicket.getId()), confirmedTicket);

        TicketResponse ticketResponse = ticketMapper.toResponse(confirmedTicket);

        TicketConfirmResponse response = new TicketConfirmResponse(ticketResponse, initPaymentResponse);
        sendConfirmEmail(response);

        return response;
    }

    private Flight confirmTicketWithRetry(Ticket existTicket, String cacheKey) {
        int retries = 0;
        boolean isConfirmed = false;
        while (retries < confirmMaxRetries && !isConfirmed) {
            try {
                Flight existFlight = flightService.getById(existTicket.getFlightId());
                Flight updatedFlight = flightService.updateAvailableSeats(existFlight, existTicket.calculateTotalSeats());

                cacheService.put(cacheKey, updatedFlight);

                logger.info("Ticket with ticketId={} update available seats successfully for FlightScheduleId={}.",
                        existTicket.getId(), existFlight.getId());
                return existFlight;
            } catch (OptimisticLockException e) {
                logger.warn("OptimisticLockException encountered on attempt {}/{} for ticketId={}. Retrying...",
                        retries, confirmMaxRetries, existTicket.getId(), e);
                retries++;
                sleep(existTicket.getId());
            } finally {
                cacheService.delete(cacheKey);
            }
        }
        logger.error("Ticket confirmation failed for ticketId={} after {} retries",
                existTicket.getId(), retries);
        throw new ApplicationException("");
    }

    private void sendConfirmEmail(TicketConfirmResponse response) {
        TicketResponse ticketResponse = response.getTicket();

        Map<String, Object> variables = new HashMap<>();
        var emailRequest = new EmailRequest(
                ticketResponse.getContactEmail(),
                "Your airline e-Ticket Confirmation",
                Template.CONFIRM_TICKET,
                variables
        );
        kafkaProducer.send(topicEmail, emailRequest);
    }

    private void sleep(String ticketId) {
        try {
            Thread.sleep(confirmRetryDelay);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            logger.error("Retry interrupted for ticketId={}", ticketId, ie);
            throw new ApplicationException(ie.getMessage());
        }
    }

    @Override
    public Ticket finalizePayment(String ticketId) {
        String cacheKey = String.format(paymentKey, ticketId);
        Ticket existTicket;
        if (!cacheService.exists(cacheKey)) {
            existTicket = ticketService.getById(ticketId);
        } else {
            existTicket = cacheService.get(cacheKey, Ticket.class);
            cacheService.delete(cacheKey);
        }
        Ticket ticket = ticketService.confirmPayment(existTicket);

        Flight bookedFlight = flightService.getById(existTicket.getFlightId());
        TicketResponse response = ticketMapper.toResponse(ticket);
        sendETicket(response);

        return ticket;
    }

    private void sendETicket(TicketResponse ticketResponse) {
        Map<String, Object> variables = new HashMap<>();
        var emailRequest = new EmailRequest(
                ticketResponse.getContactEmail(),
                "Your airline e-Ticket - Payment Successful",
                Template.TICKET_SUCCESS,
                variables
        );
        kafkaProducer.send(topicEmail, emailRequest);
    }

    @Override
    public void cancelTicket(String ticketId) {
        Ticket ticket = ticketService.getById(ticketId);
        try {
            Flight existFlight = flightService.getById(ticket.getFlightId());
            flightService.rollbackBookedSeats(existFlight, ticket.calculateTotalSeats());
            logger.info("Ticket with ticketId={} rollback seats successfully for flightId={}.",
                    ticket.getId(), existFlight.getId());
        } catch (OptimisticLockException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public TicketResponse applyDiscount(
            String ticketId,
            ApplyDiscountRequest request
    ) {
        return null;
    }
}
