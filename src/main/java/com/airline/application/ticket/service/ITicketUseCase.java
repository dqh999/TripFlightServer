package com.airline.application.ticket.service;

import com.airline.application.ticket.dataTransferObject.request.ApplyDiscountRequest;
import com.airline.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.airline.application.ticket.dataTransferObject.request.TicketBookRequest;
import com.airline.application.ticket.dataTransferObject.response.TicketBookResponse;
import com.airline.application.ticket.dataTransferObject.response.TicketResponse;
import com.airline.domain.ticket.model.Ticket;

public interface ITicketUseCase {
    TicketResponse create(TicketBookingRequest request);
    TicketBookResponse book(String ticketId, TicketBookRequest request);
    Ticket finalizePayment(String ticketId);
    void cancelTicket(String ticketId);
    TicketResponse applyDiscount(String ticketId,
                                 ApplyDiscountRequest request);
}
