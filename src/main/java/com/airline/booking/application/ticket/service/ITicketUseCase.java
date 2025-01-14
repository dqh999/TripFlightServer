package com.airline.booking.application.ticket.service;

import com.airline.booking.application.ticket.dataTransferObject.request.ApplyDiscountRequest;
import com.airline.booking.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.airline.booking.application.ticket.dataTransferObject.request.TicketBookRequest;
import com.airline.booking.application.ticket.dataTransferObject.response.TicketBookResponse;
import com.airline.booking.application.ticket.dataTransferObject.response.TicketResponse;
import com.airline.booking.domain.ticket.model.Ticket;

public interface ITicketUseCase {
    TicketResponse create(TicketBookingRequest request);
    TicketBookResponse book(String ticketId, TicketBookRequest request);
    Ticket finalizePayment(String ticketId);
    void cancelTicket(String ticketId);
    TicketResponse applyDiscount(String ticketId,
                                 ApplyDiscountRequest request);
}
