package com.airline.booking.application.ticket.service;

import com.airline.booking.application.ticket.dataTransferObject.request.ApplyDiscountRequest;
import com.airline.booking.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.airline.booking.application.ticket.dataTransferObject.request.TicketConfirmRequest;
import com.airline.booking.application.ticket.dataTransferObject.response.TicketBookingResponse;
import com.airline.booking.application.ticket.dataTransferObject.response.TicketConfirmResponse;
import com.airline.booking.application.ticket.dataTransferObject.response.TicketResponse;
import com.airline.booking.domain.ticket.model.Ticket;

public interface ITicketUseCase {
    TicketBookingResponse booking(TicketBookingRequest request);
    TicketConfirmResponse confirm(String ticketId, TicketConfirmRequest request);
    Ticket finalizePayment(String ticketId);
    void cancelTicket(String ticketId);
    TicketResponse applyDiscount(String ticketId,
                                 ApplyDiscountRequest request);
}
