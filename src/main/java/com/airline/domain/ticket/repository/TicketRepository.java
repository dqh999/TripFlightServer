package com.airline.domain.ticket.repository;

import com.airline.domain.ticket.model.Ticket;

import java.util.Optional;

public interface TicketRepository {
    Ticket save(Ticket ticket);
    Optional<Ticket> findById(String id);
}
