package com.airline.booking.domain.ticket.type;

public enum TicketStatus {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    PAID("PAID"),
    CANCELLED("CANCELLED"),
    REFUNDED("REFUNDED"),
    EXPIRED("EXPIRED");

    private final String value;

    TicketStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}