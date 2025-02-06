package com.airline.booking.application.ticket.dataTransferObject.response;

import java.time.LocalDateTime;

public class TicketBookingResponse {
    private TicketResponse ticket;
    private LocalDateTime expirationTime;

    public TicketBookingResponse() {
    }

    public TicketBookingResponse(TicketResponse ticket, LocalDateTime expirationTime) {
        this.ticket = ticket;
        this.expirationTime = expirationTime;
    }

    public TicketResponse getTicket() {
        return ticket;
    }

    public void setTicket(TicketResponse ticket) {
        this.ticket = ticket;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }
}
