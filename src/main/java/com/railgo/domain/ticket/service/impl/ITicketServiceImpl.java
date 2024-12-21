package com.railgo.domain.ticket.service.impl;

import com.railgo.domain.ticket.model.Ticket;
import com.railgo.domain.ticket.repository.TicketRepository;
import com.railgo.domain.ticket.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ITicketServiceImpl implements ITicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public ITicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
    @Override
    public Ticket bookTicket(Ticket ticket) {
        return null;
    }
}
