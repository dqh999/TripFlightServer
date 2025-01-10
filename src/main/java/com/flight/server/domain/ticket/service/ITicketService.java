package com.flight.server.domain.ticket.service;

import com.flight.server.domain.ticket.model.Ticket;
import com.flight.server.domain.utils.service.GenericService;

public interface ITicketService extends GenericService<Ticket, String> {
    Ticket confirm(Ticket ticket);
    Ticket confirmPayment(Ticket ticket);
    Ticket getTicketWithPaymentId(String id);
}
