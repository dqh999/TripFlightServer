package com.railgo.domain.ticket.repository;

import com.railgo.domain.ticket.model.Ticket;

public interface TicketRepository {
    void save(Ticket ticket);
}
