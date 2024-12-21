package com.railgo.domain.ticket.service;

import com.railgo.domain.ticket.model.Ticket;

public interface ITicketService {
    Ticket bookTicket(Ticket ticket);
}
