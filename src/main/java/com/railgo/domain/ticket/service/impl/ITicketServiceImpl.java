package com.railgo.domain.ticket.service.impl;

import com.railgo.domain.ticket.exception.TicketExceptionCode;
import com.railgo.domain.ticket.model.Ticket;
import com.railgo.domain.ticket.repository.TicketRepository;
import com.railgo.domain.ticket.service.ITicketPricingService;
import com.railgo.domain.ticket.service.ITicketService;
import com.railgo.domain.ticket.type.TicketStatus;
import com.railgo.domain.train.service.ITrainScheduleStopService;
import com.railgo.domain.utils.exception.BusinessException;
import com.railgo.domain.utils.valueObject.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ITicketServiceImpl implements ITicketService {
    private static final long CONFIRMATION_TIMEOUT_MINUTES = 15;
    private static final long PAYMENT_TIMEOUT_MINUTES = 15;

    private final ITicketPricingService ticketPricingService;
    private final TicketRepository ticketRepository;
    private final ITrainScheduleStopService trainScheduleStopService;

    @Autowired
    public ITicketServiceImpl(ITicketPricingService ticketPricingService,
                              TicketRepository ticketRepository,
                              ITrainScheduleStopService trainScheduleStopService) {
        this.ticketPricingService = ticketPricingService;
        this.ticketRepository = ticketRepository;
        this.trainScheduleStopService = trainScheduleStopService;
    }

    @Override
    public Ticket book(Ticket ticket) {
        validateTicketForBooking(ticket);

        int totalSeats = getTotalSeats(ticket);
        int availableSeats = trainScheduleStopService.calculateAvailableSeats(ticket.getTrainSchedule().getStops(), ticket.getStartStationId(), ticket.getEndStationId());
        if (totalSeats > availableSeats) {
            throw new BusinessException(TicketExceptionCode.INVALID_TICKET);
        }
        Money standardTicketPrice = ticketPricingService.calculateStandardTicketPrice(ticket.getTrainSchedule());
        Money totalPrice = ticketPricingService.calculateTicketPriceForPassengers(
                standardTicketPrice, ticket.getChildSeats(), ticket.getAdultSeats(), ticket.getSeniorSeats()
        );

        ticket.setTotalPrice(totalPrice);
        ticket.setExpirationTime(LocalDateTime.now().plusMinutes(CONFIRMATION_TIMEOUT_MINUTES));
        ticket.setStatus(TicketStatus.PENDING.getValue());

        ticketRepository.save(ticket);
        return ticket;
    }
    private void validateTicketForBooking(Ticket ticket) {
        int totalSeats = getTotalSeats(ticket);

        if (totalSeats <= 0 || totalSeats >= 10) {
            throw new BusinessException(TicketExceptionCode.INVALID_TICKET);
        }
    }
    private int getTotalSeats(Ticket ticket) {
        return ticket.getChildSeats() + ticket.getAdultSeats() + ticket.getSeniorSeats();
    }

    @Override
    public Ticket confirm(Ticket ticket) {
        validateTicketForConfirmation(ticket);

        ticket.setExpirationTime(LocalDateTime.now().plusMinutes(PAYMENT_TIMEOUT_MINUTES));
        ticket.setStatus(TicketStatus.CONFIRMED.getValue());
        ticketRepository.save(ticket);
        return ticket;
    }

    private void validateTicketForConfirmation(Ticket ticket) {
        if (!ticket.getStatus().equals(TicketStatus.PENDING.getValue())) {
            throw new BusinessException(TicketExceptionCode.INVALID_TICKET);
        }
        if (ticket.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException(TicketExceptionCode.CONFIRMATION_EXPIRED);
        }
        if (ticket.getContactEmail() == null) {
            throw new BusinessException(TicketExceptionCode.INVALID_TICKET);
        }
    }

    @Override
    public Ticket confirmPayment(Ticket ticket) {
        int totalSeats = getTotalSeats(ticket);

        trainScheduleStopService.updateAvailableSeats(
                ticket.getTrainSchedule().getStops(),
                ticket.getStartStationId(), ticket.getEndStationId(),
                totalSeats);

        ticket.setStatus(TicketStatus.PAID.getValue());
        ticketRepository.save(ticket);
        return ticket;
    }

    @Override
    public Ticket getTicket(String id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new BusinessException(TicketExceptionCode.TICKET_NOT_FOUND));
    }
}
