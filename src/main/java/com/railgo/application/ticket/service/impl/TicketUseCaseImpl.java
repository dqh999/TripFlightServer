package com.railgo.application.ticket.service.impl;

import com.railgo.application.payment.dataTransferObject.request.InitPaymentRequest;
import com.railgo.application.payment.dataTransferObject.response.InitPaymentResponse;
import com.railgo.application.payment.service.IPaymentService;
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
import com.railgo.domain.train.model.Train;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.service.ITrainScheduleService;
import com.railgo.domain.train.service.ITrainScheduleStopService;
import com.railgo.infrastructure.service.messaging.EmailService;
import com.railgo.infrastructure.util.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class TicketUseCaseImpl implements ITicketUseCase {
    private final ITicketService ticketService;
    private final TicketMapper ticketMapper;
    private final IStationUseCase stationUseCase;
    private final ITrainScheduleService trainScheduleService;
    private final ITrainScheduleStopService trainScheduleStopService;
    private final IPaymentService paymentService;
    private final EmailService emailService;

    @Autowired
    public TicketUseCaseImpl(ITicketService ticketService,
                             TicketMapper ticketMapper,
                             IStationUseCase stationUseCase,
                             ITrainScheduleService trainScheduleService,
                             ITrainScheduleStopService trainScheduleStopService,
                             IPaymentService paymentService,
                             EmailService emailService) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
        this.stationUseCase = stationUseCase;
        this.trainScheduleService = trainScheduleService;
        this.trainScheduleStopService = trainScheduleStopService;
        this.paymentService = paymentService;
        this.emailService = emailService;
    }

    @Override
    public TicketResponse book(TicketBookingRequest request) {
        String trainScheduleId = request.getTrainScheduleId();

        String startStationId = request.getStartStationId();
        String endStationId = request.getEndStationId();

        TrainSchedule trainSchedule = trainScheduleService.getScheduleByIdAndStations(trainScheduleId, startStationId, endStationId);

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
                null
        );
        Ticket newTicket = ticketService.book(ticket);

        return buildTicketResponse(newTicket);
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
        Ticket ticket = ticketService.confirm(existTicket);

        InitPaymentRequest initPaymentRequest = new InitPaymentRequest(
                request.getIpAddress(),
                request.getContactEmail(),
                ticketId,
                existTicket.getTotalPrice()
        );

        InitPaymentResponse initPaymentResponse = paymentService.init(initPaymentRequest);
        TicketResponse ticketResponse = buildTicketResponse(ticket);
        TicketConfirmationResponse response = new TicketConfirmationResponse(ticketResponse, initPaymentResponse);

        sendConfirmEmail(response);

        return response;
    }

    private void sendConfirmEmail(TicketConfirmationResponse response) {
        TicketResponse ticketResponse = response.getTicket();


        Map<String, Object> variables = buildEmailVariables(ticketResponse,response.getPayment());

        emailService.send(ticketResponse.getContactEmail(),
                "Your RailGo e-Ticket Confirmation",
                Template.CONFIRM_TICKET,
                variables);

    }

    private Map<String, Object> buildEmailVariables(TicketResponse ticketResponse, InitPaymentResponse paymentResponse) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("ticketId", ticketResponse.getId());
        variables.put("trainName", ticketResponse.getTrainName());
        variables.put("name", ticketResponse.getContactEmail());
        variables.put("departureStation", ticketResponse.getDepartureStation().getName());
        variables.put("departureTime", ticketResponse.getDepartureTime());
        variables.put("arrivalStation", ticketResponse.getArrivalStation().getName());
        variables.put("arrivalTime", ticketResponse.getArrivalTime());
        variables.put("childSeats", ticketResponse.getChildSeats());
        variables.put("adultSeats", ticketResponse.getAdultSeats());
        variables.put("seniorSeats", ticketResponse.getSeniorSeats());
        variables.put("totalPrice", ticketResponse.getTotalPrice().getValue().toString());
        variables.put("currency", ticketResponse.getTotalPrice().getCurrency());
        if (paymentResponse != null && paymentResponse.getPaymentUrl() != null) {
            variables.put("paymentUrl", paymentResponse.getPaymentUrl());
            variables.put("expiryTime",paymentResponse.getExpiryTime());
        }
        return variables;
    }

    @Override
    public void finalizePayment(String ticketId) {
        Ticket existTicket = ticketService.getTicket(ticketId);
        Ticket ticket = ticketService.confirmPayment(existTicket);

        TicketResponse ticketResponse = buildTicketResponse(ticket);

        sendETicket(ticketResponse);
    }

    private void sendETicket(TicketResponse ticketResponse) {
        Map<String, Object> variables = buildEmailVariables(ticketResponse, null);
        emailService.send(ticketResponse.getContactEmail(),
                "Your RailGo e-Ticket - Payment Successful",
                Template.TICKET_SUCCESS,
                variables);
    }

    @Override
    public TicketResponse applyDiscount(String ticketId,
                                        ApplyDiscountRequest request) {
        return null;
    }
}
