package com.railgo.application.ticket.service.impl;

import com.railgo.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.railgo.application.ticket.dataTransferObject.response.TicketResponse;
import com.railgo.application.ticket.mapper.TicketMapper;
import com.railgo.application.ticket.service.ITicketUseCase;
import com.railgo.application.train.service.ITrainScheduleUseCase;
import com.railgo.domain.ticket.model.Passenger;
import com.railgo.domain.ticket.model.Ticket;
import com.railgo.domain.ticket.service.ITicketService;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.service.ITrainScheduleService;
import com.railgo.domain.utils.valueObject.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketUseCaseImpl implements ITicketUseCase {
    private final ITicketService ticketService;
    private final TicketMapper ticketMapper;
    private final ITrainScheduleUseCase trainScheduleUseCase;
    private final ITrainScheduleService trainScheduleService;

    @Autowired
    public TicketUseCaseImpl(ITicketService ticketService,
                             TicketMapper ticketMapper,
                             ITrainScheduleUseCase trainScheduleUseCase,
                             ITrainScheduleService trainScheduleService) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
        this.trainScheduleUseCase = trainScheduleUseCase;
        this.trainScheduleService = trainScheduleService;
    }

    @Override
    public TicketResponse book(TicketBookingRequest request) {
        String trainScheduleId = request.getTrainScheduleId();

        String startStationId = request.getStartStationId();
        String endStationId = request.getEndStationId();

        TrainSchedule trainSchedule = trainScheduleService.getScheduleByIdAndStations(trainScheduleId, startStationId, endStationId);

        var trainScheduleResponse = trainScheduleUseCase.buildTrainScheduleResponse(trainSchedule, startStationId, endStationId);

        Money totalPrice = trainScheduleResponse.getTicketPrice();

        List<Passenger> passengers = ticketMapper.toPassengers(request.getPassengers());

        Ticket ticket = buildTicket(trainSchedule, startStationId, endStationId, totalPrice, passengers, request.getContactEmail());

        Ticket newTicket = ticketService.book(ticket);
        return new TicketResponse(newTicket.getId(),trainScheduleResponse.getDepartureTime(),trainScheduleResponse.getArrivalTime(),passengers.size(),totalPrice,newTicket.getStatus());
    }

    private Ticket buildTicket(TrainSchedule trainSchedule, String startStationId, String endStationId, Money totalPrice, List<Passenger> passengers,String contactEmail) {
        Ticket ticket = new Ticket();

        ticket.setTrainSchedule(trainSchedule);
        ticket.setStartStationId(startStationId);
        ticket.setEndStationId(endStationId);
        ticket.setTotalPrice(totalPrice);
        passengers.forEach(passenger -> {
            passenger.setTicketId(ticket.getId());
        });
        ticket.setPassengers(passengers);
        ticket.setContactEmail(contactEmail);

        return ticket;
    }
}
