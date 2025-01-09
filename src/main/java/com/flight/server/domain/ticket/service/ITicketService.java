package com.flight.server.domain.ticket.service;

import com.flight.server.domain.ticket.model.Ticket;

public interface ITicketService {
    Ticket create(Ticket ticket);
    Ticket confirm(Ticket ticket);
    Ticket confirmPayment(Ticket ticket);
    Ticket getTicket(String id);
    Ticket getTicketWithPaymentId(String id);
}
