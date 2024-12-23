package com.railgo.domain.ticket.service.impl;

import com.railgo.domain.ticket.exception.TicketExceptionCode;
import com.railgo.domain.ticket.model.Passenger;
import com.railgo.domain.ticket.model.Ticket;
import com.railgo.domain.ticket.repository.TicketRepository;
import com.railgo.domain.ticket.service.ITicketService;
import com.railgo.domain.ticket.type.TicketStatus;
import com.railgo.domain.train.service.ITrainScheduleStopService;
import com.railgo.domain.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ITicketServiceImpl implements ITicketService {
    private final TicketRepository ticketRepository;
    private final ITrainScheduleStopService trainScheduleStopService;

    @Autowired
    public ITicketServiceImpl(TicketRepository ticketRepository, ITrainScheduleStopService trainScheduleStopService) {
        this.ticketRepository = ticketRepository;
        this.trainScheduleStopService = trainScheduleStopService;
    }

    @Override
    public Ticket book(Ticket ticket) {
        validateTicket(ticket);

        ticket.setStatus(TicketStatus.CONFIRMED.getValue());

        trainScheduleStopService.updateAvailableSeats(ticket.getTrainSchedule().getStops(),ticket.getStartStationId(),ticket.getEndStationId(),ticket.getTotalPassengers());

        ticketRepository.save(ticket);

        return ticket;
    }

    private void validateTicket(Ticket ticket) {
        validatePassenger(ticket.getPassengers());
    }

    private void validatePassenger(List<Passenger> passengers) {
        passengers.forEach(passenger -> {
            int age = Period.between(passenger.getDateOfBirth(), LocalDate.now()).getYears();
            if (age < 16) {
                throw new BusinessException(TicketExceptionCode.INVALID_PASSENGER_AGE);
            }
        });
    }
}
