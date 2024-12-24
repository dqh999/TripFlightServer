package com.railgo.domain.ticket.service;

import com.railgo.domain.ticket.model.Ticket;

public interface ITicketService {
    Ticket book(Ticket ticket);
    void confirm(Ticket ticket);
    Ticket getTicket(String id);
}
