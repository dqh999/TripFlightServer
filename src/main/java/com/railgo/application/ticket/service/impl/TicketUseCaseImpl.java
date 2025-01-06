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
import com.railgo.domain.ticket.service.ITicketService;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.model.schedule.TrainScheduleStop;
import com.railgo.domain.train.service.ITrainScheduleService;
import com.railgo.domain.train.service.ITrainScheduleStopService;
import com.railgo.domain.utils.DateTimeUtils;
import com.railgo.domain.utils.exception.BusinessException;
import com.railgo.infrastructure.dataTransferObject.request.EmailRequest;
import com.railgo.infrastructure.component.KafkaProducer;
import com.railgo.infrastructure.service.cache.CacheService;
import com.railgo.infrastructure.util.Template;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketUseCaseImpl implements ITicketUseCase {
    private final ITicketService ticketService;
    private final TicketMapper ticketMapper;
    private final IStationUseCase stationUseCase;
    private final ITrainScheduleService trainScheduleService;
    private final ITrainScheduleStopService trainScheduleStopService;
    private final IPaymentGatewayService paymentService;
    private final KafkaProducer kafkaProducer;
    private final CacheService cacheService;

    @Value("${spring.kafka.topic.email}")
    private String topicEmail;
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY = 1000L;

    @Autowired
    public TicketUseCaseImpl(ITicketService ticketService,
                             TicketMapper ticketMapper,
                             IStationUseCase stationUseCase,
                             ITrainScheduleService trainScheduleService,
                             ITrainScheduleStopService trainScheduleStopService,
                             IPaymentGatewayService paymentService,
                             KafkaProducer kafkaProducer,
                             CacheService cacheService) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
        this.stationUseCase = stationUseCase;
        this.trainScheduleService = trainScheduleService;
        this.trainScheduleStopService = trainScheduleStopService;
        this.paymentService = paymentService;
        this.kafkaProducer = kafkaProducer;
        this.cacheService = cacheService;
    }

    @Override
    @Transactional
    public TicketResponse book(TicketBookingRequest request) {
        String trainScheduleId = request.getTrainScheduleId();

        String startStationId = request.getStartStationId();
        String endStationId = request.getEndStationId();

        int totalSeats = calculateTotalSeats(request);
        int retries = 0;
        boolean isBooked = false;
        while (retries < MAX_RETRIES && !isBooked) {
            try {
                TrainSchedule trainSchedule = trainScheduleService.getScheduleByIdAndStations(trainScheduleId, startStationId, endStationId);
                List<TrainScheduleStop> trainScheduleStops = trainSchedule.getStops();
                trainScheduleStopService.updateAvailableSeats(trainScheduleStops, totalSeats);
                isBooked = true;

                Ticket ticket = new Ticket(
                        null,
                        trainSchedule,
                        startStationId,
                        endStationId,
                        null,
                        request.getChildSeats(),
                        request.getAdultSeats(),
                        request.getSeniorSeats(),
                        null,
                        null,
                        null,
                        null
                );
                Ticket newTicket = ticketService.book(ticket);

                return buildTicketResponse(newTicket);
            } catch (OptimisticLockException e) {
                retries++;
                try {
                    Thread.sleep(RETRY_DELAY);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    System.out.println("Retry interrupted.");
                    break;
                }
            } catch (BusinessException e) {
                throw e;
            }
        }
        throw new BusinessException();
    }

    private int calculateTotalSeats(TicketBookingRequest request) {
        return request.getChildSeats() + request.getAdultSeats() + request.getSeniorSeats();
    }

    private TicketResponse buildTicketResponse(Ticket ticket) {
        TrainSchedule trainSchedule = ticket.getTrainSchedule();

        StationResponse departureStation = stationUseCase.getStation(ticket.getStartStationId());
        LocalDateTime departureTime = trainScheduleStopService.getDepartureTime(trainSchedule.getStops(), ticket.getStartStationId());
        StationResponse arrivalStation = stationUseCase.getStation(ticket.getEndStationId());
        LocalDateTime arrivalTime = trainScheduleStopService.getArrivalTime(trainSchedule.getStops(), ticket.getEndStationId());

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
        Ticket existTicket = ticketService.getTicket(ticketId);
        existTicket.setContactEmail(request.getContactEmail());

        InitPaymentRequest initPaymentRequest = new InitPaymentRequest(
                request.getIpAddress(),
                request.getContactEmail(),
                ticketId,
                existTicket.getTotalPrice()
        );
        InitPaymentResponse initPaymentResponse = paymentService.init(initPaymentRequest);

        existTicket.setPaymentId(initPaymentResponse.getPaymentId());
        Ticket ticket = ticketService.confirm(existTicket);

        TicketResponse ticketResponse = buildTicketResponse(ticket);
        TicketConfirmationResponse response = new TicketConfirmationResponse(ticketResponse, initPaymentResponse);

        sendConfirmEmail(response);

        return response;
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
        Ticket existTicket = ticketService.getTicket(ticketId);
        Ticket ticket = ticketService.confirmPayment(existTicket);

        TicketResponse ticketResponse = buildTicketResponse(ticket);

        sendETicket(ticketResponse);
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
    public TicketResponse applyDiscount(String ticketId,
                                        ApplyDiscountRequest request) {
        return null;
    }
}
