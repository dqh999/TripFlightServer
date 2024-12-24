package com.railgo.application.ticket.service.impl;

import com.railgo.application.station.dataTransferObject.response.StationResponse;
import com.railgo.application.station.service.IStationUseCase;
import com.railgo.application.ticket.dataTransferObject.request.ApplyDiscountRequest;
import com.railgo.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.railgo.application.ticket.dataTransferObject.request.TicketConfirmationRequest;
import com.railgo.application.ticket.dataTransferObject.response.TicketResponse;
import com.railgo.application.ticket.mapper.TicketMapper;
import com.railgo.application.ticket.service.ITicketUseCase;
import com.railgo.domain.ticket.model.Ticket;
import com.railgo.domain.ticket.service.ITicketService;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.service.ITrainScheduleService;
import com.railgo.domain.train.service.ITrainScheduleStopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketUseCaseImpl implements ITicketUseCase {
    private final ITicketService ticketService;
    private final TicketMapper ticketMapper;
    private final IStationUseCase stationUseCase;
    private final ITrainScheduleService trainScheduleService;
    private final ITrainScheduleStopService trainScheduleStopService;

    @Autowired
    public TicketUseCaseImpl(ITicketService ticketService,
                             TicketMapper ticketMapper,
                             IStationUseCase stationUseCase,
                             ITrainScheduleService trainScheduleService,
                             ITrainScheduleStopService trainScheduleStopService) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
        this.stationUseCase = stationUseCase;
        this.trainScheduleService = trainScheduleService;
        this.trainScheduleStopService = trainScheduleStopService;
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

        return buildTicketResponse(newTicket, trainSchedule);
    }

    private TicketResponse buildTicketResponse(Ticket ticket, TrainSchedule trainSchedule) {
        StationResponse departureStation = stationUseCase.getStation(ticket.getStartStationId());
        LocalDateTime departureTime = trainScheduleStopService.getDepartureTime(trainSchedule.getStops(), ticket.getStartStationId());
        StationResponse arrivalStation = stationUseCase.getStation(ticket.getEndStationId());
        LocalDateTime arrivalTime = trainScheduleStopService.getArrivalTime(trainSchedule.getStops(), ticket.getEndStationId());

        TicketResponse ticketResponse = ticketMapper.toDTO(ticket);
        ticketResponse.setDepartureStation(departureStation);
        ticketResponse.setArrivalStation(arrivalStation);
        ticketResponse.setDepartureTime(departureTime);
        ticketResponse.setArrivalTime(arrivalTime);
        return ticketResponse;
    }

    @Override
    public void confirm(String ticketId,
                        TicketConfirmationRequest request) {
        Ticket existTicket = ticketService.getTicket(ticketId);
        existTicket.setContactEmail(request.getContactEmail());
        ticketService.confirm(existTicket);
    }

    @Override
    public TicketResponse applyDiscount(String ticketId,
                                        ApplyDiscountRequest request) {
        return null;
    }
}
