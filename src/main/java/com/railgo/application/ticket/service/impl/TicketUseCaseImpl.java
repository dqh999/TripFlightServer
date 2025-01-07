package com.railgo.application.ticket.service.impl;

import com.railgo.application.payment.dataTransferObject.request.InitPaymentRequest;
import com.railgo.application.payment.dataTransferObject.response.InitPaymentResponse;
import com.railgo.application.payment.service.IPaymentGatewayService;
import com.railgo.application.station.dataTransferObject.response.StationResponse;
import com.railgo.application.station.service.IStationUseCase;
import com.railgo.application.ticket.dataTransferObject.request.ApplyDiscountRequest;
import com.railgo.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.railgo.application.ticket.dataTransferObject.request.TicketConfirmationRequest;
import com.railgo.application.ticket.dataTransferObject.response.TicketConfirmationResponse;
import com.railgo.application.ticket.dataTransferObject.response.TicketResponse;
import com.railgo.application.ticket.mapper.TicketMapper;
import com.railgo.application.ticket.service.ITicketUseCase;
import com.railgo.domain.ticket.model.Ticket;
import com.railgo.domain.ticket.service.ITicketPricingService;
import com.railgo.domain.ticket.service.ITicketService;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.model.schedule.TrainScheduleStop;
import com.railgo.domain.train.service.ITrainScheduleService;
import com.railgo.domain.train.service.ITrainScheduleStopService;
import com.railgo.domain.utils.DateTimeUtils;
import com.railgo.domain.utils.exception.BusinessException;
import com.railgo.domain.utils.valueObject.Money;
import com.railgo.infrastructure.dataTransferObject.request.EmailRequest;
import com.railgo.application.component.KafkaProducer;
import com.railgo.infrastructure.service.cache.CacheService;
import com.railgo.infrastructure.util.Template;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
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


    @Value("${ticket.confirm.key}")
    private String confirmKey;
    @Value("${ticket.confirm.timeout}")
    private long confirmTimeout;
    @Value("${ticket.confirm.max-retries}")
    private int confirmMaxRetries = 3;
    @Value("${ticket.confirm.retry-delay}")
    private long confirmRetryDelay = 1000L;

    @Value("${ticket.payment.key}")
    private String paymentKey;
    @Value("${ticket.payment.timeout}")
    private long paymentTimeout;

    @Autowired
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
    @Transactional
    public TicketResponse book(TicketBookingRequest request) {
        logger.info("Start booking ticket with request: {}", request);
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

        Ticket newTicket = ticketService.book(ticket);

        String key = String.format(confirmKey, newTicket.getId());
        cacheService.put(key, newTicket,confirmTimeout);

        logger.info("Ticket booking successful. Ticket ID: {}", newTicket.getId());
        return buildTicketResponse(newTicket, trainSchedule);

    }

    private TicketResponse buildTicketResponse(Ticket ticket, TrainSchedule trainSchedule) {
        StationResponse departureStation = stationUseCase.getStation(ticket.getStartStationId());
        LocalDateTime departureTime = trainSchedule.getStops().getFirst().getDepartureTime();
        StationResponse arrivalStation = stationUseCase.getStation(ticket.getEndStationId());
        LocalDateTime arrivalTime = trainSchedule.getStops().getLast().getArrivalTime();

        TicketResponse ticketResponse = ticketMapper.toDTO(ticket);
        ticketResponse.setTrainName(trainSchedule.getTrain().getName());
        ticketResponse.setDepartureStation(departureStation);
        ticketResponse.setArrivalStation(arrivalStation);
        ticketResponse.setDepartureTime(departureTime);
        ticketResponse.setArrivalTime(arrivalTime);
        return ticketResponse;
    }

    @Override
    public TicketConfirmationResponse confirm(String ticketId,
                                              TicketConfirmationRequest request) {
        logger.info("Confirming ticket with ticketId={}", ticketId);
        String cacheKey = String.format(confirmKey, ticketId);

        if (!cacheService.exists(cacheKey)) {
            throw new BusinessException("Ticket not found in cache.");
        }

        Ticket existTicket = cacheService.get(cacheKey, Ticket.class);
        logger.info("Ticket booked. Ticket: {}", existTicket.toString());
        TrainSchedule trainSchedule = confirmTicketWithRetry(existTicket,cacheKey);

        existTicket.setContactEmail(request.getContactEmail());

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

        TicketResponse ticketResponse = buildTicketResponse(ticket, trainSchedule);
        TicketConfirmationResponse response = new TicketConfirmationResponse(ticketResponse, initPaymentResponse);

        sendConfirmEmail(response);

        cacheService.put("payment_pending_%s".formatted(initPaymentResponse.getPaymentId()), initPaymentResponse);
        return response;
    }

    private TrainSchedule confirmTicketWithRetry(Ticket existTicket, String cacheKey) {
        int retries = 0;
        boolean isConfirmed = false;
        while (retries < confirmMaxRetries && !isConfirmed) {
            try {
                TrainSchedule trainSchedule = trainScheduleService
                        .getScheduleByIdAndStations(
                                existTicket.getTrainScheduleId(),
                                existTicket.getStartStationId(),
                                existTicket.getEndStationId());
                List<TrainScheduleStop> trainScheduleStops = trainSchedule.getStops();
                trainScheduleStopService.updateAvailableSeats(trainScheduleStops, existTicket.calculateTotalSeats());
                logger.info("Ticket with ticketId={} update available seats successfully for trainScheduleId={}.",
                        existTicket.getId(), trainSchedule.getId());
                return trainSchedule;
            } catch (OptimisticLockException e) {
                logger.warn("OptimisticLockException encountered on attempt {}/{} for ticketId={}. Retrying...",
                        retries, confirmMaxRetries, existTicket.getId(), e);
                retries++;
                try {
                    Thread.sleep(confirmRetryDelay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    logger.error("Retry interrupted for ticketId={}", existTicket.getId(), ie);
                    break;
                } catch (BusinessException ie) {
                    break;
                } catch (Exception ex){
                    logger.error("Unexpected error occurred for ticketId={}. Retrying...", existTicket.getId(), ex);
                    if (retries >= confirmMaxRetries) {
                        logger.error("Max retries reached for ticketId={} due to unexpected error. Aborting.", existTicket.getId());
                        break;
                    }
                    try {
                        Thread.sleep(confirmRetryDelay);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        logger.error("Retry interrupted for ticketId={}", existTicket.getId(), ie);
                        break;
                    }
                }
            } finally {
                cacheService.delete(cacheKey);
            }
        }
        logger.error("Ticket confirmation failed for ticketId={} after {} retries",
                existTicket.getId(), retries);
        throw new BusinessException();
    }

    private void sendConfirmEmail(TicketConfirmationResponse response) {
        TicketResponse ticketResponse = response.getTicket();

        Map<String, Object> variables = buildEmailVariables(ticketResponse, response.getPayment());

        var emailRequest = new EmailRequest(
                ticketResponse.getContactEmail(),
                "Your RailGo e-Ticket Confirmation",
                Template.CONFIRM_TICKET,
                variables
        );
        kafkaProducer.send(topicEmail, emailRequest);
    }

    private Map<String, Object> buildEmailVariables(TicketResponse ticketResponse, InitPaymentResponse paymentResponse) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("ticketId", ticketResponse.getId());
        variables.put("trainName", ticketResponse.getTrainName());
        variables.put("name", ticketResponse.getContactEmail());
        variables.put("departureStation", ticketResponse.getDepartureStation().getName());
        variables.put("departureTime", DateTimeUtils.formatDateTime(ticketResponse.getDepartureTime()));
        variables.put("arrivalStation", ticketResponse.getArrivalStation().getName());
        variables.put("arrivalTime", DateTimeUtils.formatDateTime(ticketResponse.getArrivalTime()));
        variables.put("childSeats", ticketResponse.getChildSeats());
        variables.put("adultSeats", ticketResponse.getAdultSeats());
        variables.put("seniorSeats", ticketResponse.getSeniorSeats());
        variables.put("totalPrice", ticketResponse.getTotalPrice().getValue().toString());
        variables.put("currency", ticketResponse.getTotalPrice().getCurrency());
        if (paymentResponse != null && paymentResponse.getPaymentUrl() != null) {
            variables.put("paymentGateway", paymentResponse.getPaymentGateway());
            variables.put("paymentUrl", paymentResponse.getPaymentUrl());
            variables.put("expiryTime", DateTimeUtils.formatDateTime(paymentResponse.getExpiryTime()));
        }
        return variables;
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

        TrainSchedule trainSchedule = trainScheduleService
                .getScheduleByIdAndStations(
                        ticket.getId(),
                        ticket.getStartStationId(),
                        ticket.getEndStationId());
        TicketResponse ticketResponse = buildTicketResponse(ticket, trainSchedule);

        sendETicket(ticketResponse);
        cacheService.delete("payment_pending_%s".formatted(ticket.getPaymentId()));
        return ticket;
    }

    private void sendETicket(TicketResponse ticketResponse) {
        Map<String, Object> variables = buildEmailVariables(ticketResponse, null);
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
        int retries = 0;
        boolean isConfirmed = false;
        while (retries < confirmMaxRetries && !isConfirmed) {
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
                retries++;
                try {
                    Thread.sleep(confirmRetryDelay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    logger.error("Retry interrupted for ticketId={}", ticket.getId(), ie);
                    break;
                }
            }
        }
    }

    @Override
    public TicketResponse applyDiscount(String ticketId, ApplyDiscountRequest request) {
        return null;
    }
}
