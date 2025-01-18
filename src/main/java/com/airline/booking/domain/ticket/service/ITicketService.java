package com.airline.booking.domain.ticket.service;

import com.airline.booking.domain.ticket.model.Ticket;
import com.airline.booking.domain.utils.service.CURDService;

public interface ITicketService extends CURDService<Ticket, String> {
    Ticket confirm(Ticket ticket);
    Ticket confirmPayment(Ticket ticket);
    Ticket getTicketWithPaymentId(String id);
}
