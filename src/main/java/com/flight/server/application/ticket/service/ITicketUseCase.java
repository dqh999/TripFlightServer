package com.flight.server.application.ticket.service;

import com.flight.server.application.ticket.dataTransferObject.request.ApplyDiscountRequest;
import com.flight.server.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.flight.server.application.ticket.dataTransferObject.request.TicketBookRequest;
import com.flight.server.application.ticket.dataTransferObject.response.TicketBookResponse;
import com.flight.server.application.ticket.dataTransferObject.response.TicketResponse;
import com.flight.server.domain.ticket.model.Ticket;

public interface ITicketUseCase {
    TicketResponse create(TicketBookingRequest request);
    TicketBookResponse book(String ticketId, TicketBookRequest request);
    Ticket finalizePayment(String ticketId);
    void cancelTicket(String ticketId);
    TicketResponse applyDiscount(String ticketId,
                                 ApplyDiscountRequest request);
}
