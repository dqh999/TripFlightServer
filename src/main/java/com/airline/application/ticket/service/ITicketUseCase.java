package com.railgo.application.ticket.service;

import com.railgo.application.ticket.dataTransferObject.request.ApplyDiscountRequest;
import com.railgo.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.railgo.application.ticket.dataTransferObject.request.TicketBookRequest;
import com.railgo.application.ticket.dataTransferObject.response.TicketBookResponse;
import com.railgo.application.ticket.dataTransferObject.response.TicketResponse;
import com.railgo.domain.ticket.model.Ticket;

public interface ITicketUseCase {
    TicketResponse create(TicketBookingRequest request);
    TicketBookResponse book(String ticketId, TicketBookRequest request);
    Ticket finalizePayment(String ticketId);
    void cancelTicket(String ticketId);
    TicketResponse applyDiscount(String ticketId,
                                 ApplyDiscountRequest request);
}
