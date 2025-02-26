package com.airline.booking.domain.ticket.repository;

import com.airline.booking.domain.ticket.model.Ticket;

import java.util.Optional;

public interface TicketRepository {
    Ticket save(Ticket ticket);
    Optional<Ticket> findById(String id);
}
