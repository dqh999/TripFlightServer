package com.airline.booking.domain.ticket.service;

import com.airline.booking.domain.ticket.model.Ticket;
import com.airline.booking.domain.utils.service.GenericService;

public interface ITicketService extends GenericService<Ticket, String> {
    Ticket confirm(Ticket ticket);
    Ticket confirmPayment(Ticket ticket);
    Ticket getTicketWithPaymentId(String id);
}
