package com.railgo.domain.ticket.repository;

import com.railgo.domain.ticket.model.Ticket;

import java.util.Optional;

public interface TicketRepository {
    Ticket save(Ticket ticket);
    Optional<Ticket> findById(String id);
    Optional<Ticket> findByPaymentId(String paymentId);
}
