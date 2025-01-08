package com.airline.application.ticket.service.impl;

import com.airline.application.airport.service.IAirportUseCase;
import com.airline.application.payment.dataTransferObject.request.InitPaymentRequest;
import com.airline.application.payment.dataTransferObject.response.InitPaymentResponse;
import com.airline.application.payment.service.IPaymentGatewayService;
import com.airline.application.ticket.dataTransferObject.request.ApplyDiscountRequest;
import com.airline.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.airline.application.ticket.dataTransferObject.request.TicketBookRequest;
import com.airline.application.ticket.dataTransferObject.response.TicketBookResponse;
import com.airline.application.ticket.dataTransferObject.response.TicketResponse;
import com.airline.application.ticket.mapper.TicketMapper;
import com.airline.application.ticket.service.ITicketUseCase;
import com.airline.application.ticket.utils.TicketUtils;
import com.airline.application.utils.exception.ApplicationException;
import com.airline.domain.airplane.model.Flight;
import com.airline.domain.airplane.service.IFlightScheduleService;
import com.airline.domain.ticket.model.Ticket;
import com.airline.domain.ticket.service.ITicketService;
import com.airline.domain.utils.valueObject.Money;
import com.airline.infrastructure.dataTransferObject.request.EmailRequest;
import com.airline.application.component.KafkaProducer;
import com.airline.infrastructure.service.cache.CacheService;
import com.airline.infrastructure.util.Template;
import jakarta.persistence.OptimisticLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TicketUseCaseImpl implements ITicketUseCase {
    private static final Logger logger = LoggerFactory.getLogger(TicketUseCaseImpl.class);

    private final ITicketService ticketService;
    private final TicketMapper ticketMapper;
    private final IAirportUseCase airportUseCase;
    private final IFlightScheduleService flightScheduleService;
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
                             TicketMapper ticketMapper,
                             IAirportUseCase airportUseCase,
                             IFlightScheduleService flightScheduleService,
                             IPaymentGatewayService paymentGatewayService,
                             KafkaProducer kafkaProducer,
                             CacheService cacheService) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
        this.airportUseCase = airportUseCase;
        this.flightScheduleService = flightScheduleService;
        this.paymentGatewayService = paymentGatewayService;
        this.kafkaProducer = kafkaProducer;
        this.cacheService = cacheService;
    }

    @Override
    public TicketResponse create(TicketBookingRequest request) {
        String FlightScheduleId = request.getFlightScheduleId();

        String startairlineId = request.getStartairlineId();
        String endairlineId = request.getEndairlineId();

        Flight Flight = flightScheduleService
                .g(
                        FlightScheduleId,
                        startairlineId,
                        endairlineId);
        Money standardTicketPrice = ticketPricingService.calculateStandardTicketPrice(Flight);

        Money totalPrice = ticketPricingService.calculateTicketPriceForPassengers(
                standardTicketPrice,
                request.getChildSeats(),
                request.getAdultSeats(),
                request.getSeniorSeats()
        );
        Ticket ticket = new Ticket(
                null,
                FlightScheduleId,
                startairlineId,
                endairlineId,
                totalPrice,
                request.getChildSeats(),
                request.getAdultSeats(),
                request.getSeniorSeats(),
                null,
                null,
                null,
                null
        );
        Ticket newTicket = ticketService.create(ticket);
        String key = String.format(confirmKey, newTicket.getId());
        cacheService.put(key, newTicket, confirmTimeout);

        logger.info("Ticket creating successful. Ticket ID: {}", newTicket.getId());
        return TicketUtils.buildTicketResponse(newTicket, Flight, airlineUseCase, ticketMapper);

    }

    @Override
    public TicketBookResponse book(String ticketId,
                                   TicketBookRequest request) {
        String cacheKey = String.format(confirmKey, ticketId);

        if (!cacheService.exists(cacheKey)) {
            throw new ApplicationException("Ticket has not been booked!");
        }

        Ticket existTicket = cacheService.get(cacheKey, Ticket.class);
        existTicket.setContactEmail(request.getContactEmail());
        logger.info("Ticket booked. Ticket: {}", existTicket);

        Flight Flight = confirmTicketWithRetry(existTicket, cacheKey);

        InitPaymentRequest initPaymentRequest = new InitPaymentRequest(
                request.getIpAddress(),
                request.getContactEmail(),
                ticketId,
                existTicket.getTotalPrice()
        );
        InitPaymentResponse initPaymentResponse = paymentGatewayService.init(initPaymentRequest);

        existTicket.setPaymentId(initPaymentResponse.getPaymentId());
        Ticket ticket = ticketService.confirm(existTicket);

        cacheService.put(String.format(paymentKey, existTicket.getId()), ticket);

        TicketResponse ticketResponse = TicketUtils.buildTicketResponse(ticket, Flight, airlineUseCase, ticketMapper);
        TicketBookResponse response = new TicketBookResponse(ticketResponse, initPaymentResponse);

        sendConfirmEmail(response);

        return response;
    }

    private Flight confirmTicketWithRetry(Ticket existTicket, String cacheKey) {
        int retries = 0;
        boolean isConfirmed = false;
        while (retries < confirmMaxRetries && !isConfirmed) {
            try {
                Flight Flight = getFlightSchedule(
                        existTicket.getFlightScheduleId(),
                        existTicket.getStartairlineId(),
                        existTicket.getEndairlineId()
                );
                List<FlightScheduleStop> FlightScheduleStops = Flight.getStops();
                FlightScheduleStopService.updateAvailableSeats(FlightScheduleStops, existTicket.calculateTotalSeats());
                logger.info("Ticket with ticketId={} update available seats successfully for FlightScheduleId={}.",
                        existTicket.getId(), Flight.getId());
                return Flight;
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

    private Flight getFlightSchedule(String FlightScheduleId, String startairlineId, String endairlineId) {
        return FlightScheduleService.getScheduleByIdAndairlines(
                FlightScheduleId,
                startairlineId,
                endairlineId);
    }

    private void sendConfirmEmail(TicketBookResponse response) {
        TicketResponse ticketResponse = response.getTicket();

        Map<String, Object> variables = TicketUtils.buildEmailVariables(ticketResponse, response.getPayment());

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
            existTicket = ticketService.getTicket(ticketId);
        } else {
            existTicket = cacheService.get(cacheKey, Ticket.class);
            cacheService.delete(cacheKey);
        }
        Ticket ticket = ticketService.confirmPayment(existTicket);

        Flight flight = getFlightSchedule(
                ticket.getId(),
                ticket.getStartairlineId(),
                ticket.getEndairlineId()
        );
        TicketResponse ticketResponse = TicketUtils.buildTicketResponse(
                ticket,
                flight,
                airlineUseCase,
                ticketMapper
        );

        sendETicket(ticketResponse);
        return ticket;
    }

    private void sendETicket(TicketResponse ticketResponse) {
        Map<String, Object> variables = TicketUtils.buildEmailVariables(ticketResponse, null);
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
        Ticket ticket = ticketService.getTicket(ticketId);
        try {
            Flight flight = FlightScheduleService
                    .getScheduleByIdAndairlines(
                            ticket.getFlightScheduleId(),
                            ticket.getStartairlineId(),
                            ticket.getEndairlineId());
            List<FlightScheduleStop> FlightScheduleStops = Flight.getStops();
            FlightScheduleStopService.rollbackSeats(FlightScheduleStops, ticket.calculateTotalSeats());
            logger.info("Ticket with ticketId={} rollback seats successfully for FlightScheduleId={}.",
                    ticket.getId(), flight.getId());
        } catch (OptimisticLockException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public TicketResponse applyDiscount(String ticketId, ApplyDiscountRequest request) {
        return null;
    }
}
