package graph.railgo.domain.ticket.service;

import graph.railgo.domain.ticket.model.Ticket;

public interface ITicketBookingService {
    Ticket bookTicket(Ticket ticket);
}
