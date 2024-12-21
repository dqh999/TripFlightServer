package com.railgo.application.ticket.service;

import com.railgo.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.railgo.application.ticket.dataTransferObject.response.TicketResponse;

public interface ITicketUseCase {
    TicketResponse booking(TicketBookingRequest request);
}
