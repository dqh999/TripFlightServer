package com.railgo.application.ticket.service;

import com.railgo.application.ticket.dataTransferObject.request.ApplyDiscountRequest;
import com.railgo.application.ticket.dataTransferObject.request.TicketBookingRequest;
import com.railgo.application.ticket.dataTransferObject.request.TicketConfirmationRequest;
import com.railgo.application.ticket.dataTransferObject.response.TicketConfirmationResponse;
import com.railgo.application.ticket.dataTransferObject.response.TicketResponse;

public interface ITicketUseCase {
    TicketResponse book(TicketBookingRequest request);
    TicketConfirmationResponse confirm(String ticketId, TicketConfirmationRequest request);
    void finalizePayment(String ticketId);
    TicketResponse applyDiscount(String ticketId,
                                 ApplyDiscountRequest request);
}
