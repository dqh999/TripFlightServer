package com.railgo.application.ticket.service.impl;

import com.railgo.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.railgo.application.ticket.dataTransferObject.response.TicketResponse;
import com.railgo.application.ticket.service.ITicketUseCase;
import com.railgo.domain.ticket.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketUseCaseImpl implements ITicketUseCase {
    private final ITicketService ticketService;

    @Autowired
    public TicketUseCaseImpl(ITicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public TicketResponse booking(TicketBookingRequest request) {

        return null;
    }
}
