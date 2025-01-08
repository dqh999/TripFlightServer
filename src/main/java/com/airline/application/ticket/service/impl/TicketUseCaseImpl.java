package com.railgo.application.ticket.service.impl;

import com.railgo.application.payment.dataTransferObject.request.InitPaymentRequest;
import com.railgo.application.payment.dataTransferObject.response.InitPaymentResponse;
import com.railgo.application.payment.service.IPaymentGatewayService;
import com.railgo.application.station.service.IStationUseCase;
import com.railgo.application.ticket.dataTransferObject.request.ApplyDiscountRequest;
import com.railgo.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.railgo.application.ticket.dataTransferObject.request.TicketBookRequest;
import com.railgo.application.ticket.dataTransferObject.response.TicketBookResponse;
import com.railgo.application.ticket.dataTransferObject.response.TicketResponse;
import com.railgo.application.ticket.mapper.TicketMapper;
import com.railgo.application.ticket.service.ITicketUseCase;
import com.railgo.application.ticket.utils.TicketUtils;
import com.railgo.application.utils.exception.ApplicationException;
import com.railgo.domain.ticket.model.Ticket;
import com.railgo.domain.ticket.service.ITicketPricingService;
import com.railgo.domain.ticket.service.ITicketService;
import com.railgo.domain.ticket.type.TicketStatus;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.model.schedule.TrainScheduleStop;
import com.railgo.domain.train.service.ITrainScheduleService;
import com.railgo.domain.train.service.ITrainScheduleStopService;
import com.railgo.domain.utils.valueObject.Money;
import com.railgo.infrastructure.dataTransferObject.request.EmailRequest;
import com.railgo.application.component.KafkaProducer;
import com.railgo.infrastructure.service.cache.CacheService;
import com.railgo.infrastructure.util.Template;
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
    private final ITicketPricingService ticketPricingService;
    private final TicketMapper ticketMapper;
    private final IStationUseCase stationUseCase;
    private final ITrainScheduleService trainScheduleService;
    private final ITrainScheduleStopService trainScheduleStopService;
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
                             ITicketPricingService ticketPricingService,
                             TicketMapper ticketMapper,
                             IStationUseCase stationUseCase,
                             ITrainScheduleService trainScheduleService,
                             ITrainScheduleStopService trainScheduleStopService,
                             IPaymentGatewayService paymentGatewayService,
                             KafkaProducer kafkaProducer,
                             CacheService cacheService) {
        this.ticketService = ticketService;
        this.ticketPricingService = ticketPricingService;
        this.ticketMapper = ticketMapper;
        this.stationUseCase = stationUseCase;
        this.trainScheduleService = trainScheduleService;
        this.trainScheduleStopService = trainScheduleStopService;
        this.paymentGatewayService = paymentGatewayService;
        this.kafkaProducer = kafkaProducer;
        this.cacheService = cacheService;
    }

    @Override
    public TicketResponse create(TicketBookingRequest request) {
        String trainScheduleId = request.getTrainScheduleId();

        String startStationId = request.getStartStationId();
        String endStationId = request.getEndStationId();

        TrainSchedule trainSchedule = trainScheduleService
                .getScheduleByIdAndStations(
                        trainScheduleId,
                        startStationId,
                        endStationId);
        Money standardTicketPrice = ticketPricingService.calculateStandardTicketPrice(trainSchedule);

        Money totalPrice = ticketPricingService.calculateTicketPriceForPassengers(
                standardTicketPrice,
                request.getChildSeats(),
                request.getAdultSeats(),
                request.getSeniorSeats()
        );
        Ticket ticket = new Ticket(
                null,
                trainScheduleId,
                startStationId,
                endStationId,
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
        return TicketUtils.buildTicketResponse(newTicket, trainSchedule, stationUseCase, ticketMapper);

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

        TrainSchedule trainSchedule = confirmTicketWithRetry(existTicket, cacheKey);

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

        TicketResponse ticketResponse = TicketUtils.buildTicketResponse(ticket, trainSchedule, stationUseCase, ticketMapper);
        TicketBookResponse response = new TicketBookResponse(ticketResponse, initPaymentResponse);

        sendConfirmEmail(response);

        return response;
    }

    private TrainSchedule confirmTicketWithRetry(Ticket existTicket, String cacheKey) {
        int retries = 0;
        boolean isConfirmed = false;
        while (retries < confirmMaxRetries && !isConfirmed) {
            try {
                TrainSchedule trainSchedule = getTrainSchedule(
                        existTicket.getTrainScheduleId(),
                        existTicket.getStartStationId(),
                        existTicket.getEndStationId()
                );
                List<TrainScheduleStop> trainScheduleStops = trainSchedule.getStops();
                trainScheduleStopService.updateAvailableSeats(trainScheduleStops, existTicket.calculateTotalSeats());
                logger.info("Ticket with ticketId={} update available seats successfully for trainScheduleId={}.",
                        existTicket.getId(), trainSchedule.getId());
                return trainSchedule;
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

    private TrainSchedule getTrainSchedule(String trainScheduleId, String startStationId, String endStationId) {
        return trainScheduleService.getScheduleByIdAndStations(
                trainScheduleId,
                startStationId,
                endStationId);
    }

    private void sendConfirmEmail(TicketBookResponse response) {
        TicketResponse ticketResponse = response.getTicket();

        Map<String, Object> variables = TicketUtils.buildEmailVariables(ticketResponse, response.getPayment());

        var emailRequest = new EmailRequest(
                ticketResponse.getContactEmail(),
                "Your RailGo e-Ticket Confirmation",
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

        TrainSchedule trainSchedule = getTrainSchedule(
                ticket.getId(),
                ticket.getStartStationId(),
                ticket.getEndStationId()
        );
        TicketResponse ticketResponse = TicketUtils.buildTicketResponse(
                ticket,
                trainSchedule,
                stationUseCase,
                ticketMapper
        );

        sendETicket(ticketResponse);
        return ticket;
    }

    private void sendETicket(TicketResponse ticketResponse) {
        Map<String, Object> variables = TicketUtils.buildEmailVariables(ticketResponse, null);
        var emailRequest = new EmailRequest(
                ticketResponse.getContactEmail(),
                "Your RailGo e-Ticket - Payment Successful",
                Template.TICKET_SUCCESS,
                variables
        );
        kafkaProducer.send(topicEmail, emailRequest);
    }

    @Override
    public void cancelTicket(String ticketId) {
        Ticket ticket = ticketService.getTicket(ticketId);
        try {
            TrainSchedule trainSchedule = trainScheduleService
                    .getScheduleByIdAndStations(
                            ticket.getTrainScheduleId(),
                            ticket.getStartStationId(),
                            ticket.getEndStationId());
            List<TrainScheduleStop> trainScheduleStops = trainSchedule.getStops();
            trainScheduleStopService.rollbackSeats(trainScheduleStops, ticket.calculateTotalSeats());
            logger.info("Ticket with ticketId={} rollback seats successfully for trainScheduleId={}.",
                    ticket.getId(), trainSchedule.getId());
        } catch (OptimisticLockException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public TicketResponse applyDiscount(String ticketId, ApplyDiscountRequest request) {
        return null;
    }
}
