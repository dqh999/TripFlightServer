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
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.service.ITrainScheduleService;
import com.railgo.domain.train.service.ITrainScheduleStopService;
import com.railgo.infrastructure.service.messaging.EmailService;
import com.railgo.infrastructure.util.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        StationResponse departureStation = stationUseCase.getStation(ticket.getStartStationId());
        LocalDateTime departureTime = trainScheduleStopService.getDepartureTime(ticket.getTrainSchedule().getStops(), ticket.getStartStationId());
        StationResponse arrivalStation = stationUseCase.getStation(ticket.getEndStationId());
        LocalDateTime arrivalTime = trainScheduleStopService.getArrivalTime(ticket.getTrainSchedule().getStops(), ticket.getEndStationId());

        TicketResponse ticketResponse = ticketMapper.toDTO(ticket);
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
        emailService.send(request.getContactEmail(), Template.CONFIRM_TICKET, Map.of(
                "name", "John Doe",
                "ticketId", "12345",
                "trainName", "Express Train 123",
                "date", "2024-12-25",
                "amount", "700,000 VND",
                "paymentMethod", "Credit Card"
        ));

        InitPaymentResponse initPaymentResponse = paymentService.init(initPaymentRequest);
        TicketResponse ticketResponse = buildTicketResponse(ticket);
        return new TicketConfirmationResponse(ticketResponse,initPaymentResponse);
    }
    private void sendTicketConfirmationEmail(String email, Ticket ticket) {
        Map<String, Object> emailVariables = buildEmailVariables(ticket);
        emailService.send(email, Template.CONFIRM_TICKET, emailVariables);
    }

    // Phương thức xây dựng các biến cho email
    private Map<String, Object> buildEmailVariables(Ticket ticket) {
        return Map.of(
                "name", ticket.getContactName(),
                "ticketId", ticket.getId(),
                "trainName", ticket.getTrainName(),
                "date", ticket.getTravelDate().toString(),
                "amount", formatAmount(ticket.getTotalPrice()),
                "paymentMethod", ticket.getPaymentMethod()
        );
    }
    @Override
    public void finalizePayment(String ticketId) {
        Ticket existTicket = ticketService.getTicket(ticketId);
        Ticket ticket = ticketService.confirmPayment(existTicket);

        String contactEmail = ticket.getContactEmail();
        System.out.println("Confirm payment success!");
    }

    @Override
    public TicketResponse applyDiscount(String ticketId,
                                        ApplyDiscountRequest request) {
        return null;
    }
}
