package com.airline.booking.domain.ticket.service.impl;

import com.airline.booking.domain.ticket.exception.TicketExceptionCode;
import com.airline.booking.domain.ticket.model.Ticket;
import com.airline.booking.domain.ticket.repository.TicketRepository;
import com.airline.booking.domain.ticket.service.ITicketService;
import com.airline.booking.domain.ticket.type.TicketStatus;
import com.airline.booking.domain.utils.DateTimeUtils;
import com.airline.booking.domain.exception.BusinessException;
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
    public Ticket create(Ticket ticket) {
        validateTicketForBooking(ticket);

        ticket.setExpirationTime(DateTimeUtils.nowInVietnam().plusMinutes(CONFIRMATION_TIMEOUT_MINUTES));
        ticket.setStatus(TicketStatus.PENDING.getValue());

        return ticket;
    }

    private void validateTicketForBooking(Ticket ticket) {
        int totalSeats = ticket.calculateTotalSeats();

        if (totalSeats <= 0 || totalSeats >= 10) {
            throw new BusinessException(TicketExceptionCode.INVALID_TICKET);
        }
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
        ticket.setStatus(TicketStatus.PAID.getValue());
        ticketRepository.save(ticket);
        return ticket;
    }

    @Override
    public Ticket update(Ticket ticket) {
        return null;
    }

    @Override
    public void delete(Ticket ticket) {

    }

    @Override
    public Ticket getById(String id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new BusinessException(TicketExceptionCode.TICKET_NOT_FOUND));
    }

    @Override
    public Ticket getTicketWithPaymentId(String id) {
        return null;
    }
}
