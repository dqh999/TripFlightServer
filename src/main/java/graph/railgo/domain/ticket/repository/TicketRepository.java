package graph.railgo.domain.ticket.repository;

import graph.railgo.domain.ticket.model.Ticket;

public interface TicketRepository {
    void save(Ticket ticket);
}
