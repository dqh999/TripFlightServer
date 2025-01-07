package com.railgo.domain.ticket.service.impl;

import com.railgo.domain.ticket.exception.TicketExceptionCode;
import com.railgo.domain.ticket.model.Ticket;
import com.railgo.domain.ticket.repository.TicketRepository;
import com.railgo.domain.ticket.service.ITicketPricingService;
import com.railgo.domain.ticket.service.ITicketService;
import com.railgo.domain.ticket.type.TicketStatus;
import com.railgo.domain.train.service.ITrainScheduleStopService;
import com.railgo.domain.utils.DateTimeUtils;
import com.railgo.domain.utils.exception.BusinessException;
import com.railgo.domain.utils.valueObject.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ITicketServiceImpl implements ITicketService {
    private static final long CONFIRMATION_TIMEOUT_MINUTES = 10;
    private static final long PAYMENT_TIMEOUT_MINUTES = 30;

    private final TicketRepository ticketRepository;

    @Autowired
    public ITicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket book(Ticket ticket) {
        validateTicketForBooking(ticket);

        ticket.setExpirationTime(DateTimeUtils.nowInVietnam().plusMinutes(CONFIRMATION_TIMEOUT_MINUTES));
        ticket.setStatus(TicketStatus.PENDING.getValue());

        return ticketRepository.save(ticket);
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

        ticket.setExpirationTime(DateTimeUtils.nowInVietnam().plusMinutes(PAYMENT_TIMEOUT_MINUTES));
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

//        trainScheduleStopService.updateAvailableSeats(
//                ticket.getTrainSchedule().getStops(),
//                totalSeats);

        ticket.setStatus(TicketStatus.PAID.getValue());
        ticketRepository.save(ticket);
        return ticket;
    }

    @Override
    public Ticket getTicket(String id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new BusinessException(TicketExceptionCode.TICKET_NOT_FOUND));
    }

    @Override
    public Ticket getTicketWithPaymentId(String id) {
        return null;
    }
}
