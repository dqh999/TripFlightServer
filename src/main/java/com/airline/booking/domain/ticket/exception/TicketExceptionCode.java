package com.airline.booking.domain.ticket.exception;

import com.airline.booking.domain.exception.ExceptionCode;

public enum TicketExceptionCode implements ExceptionCode {
    TICKET_NOT_FOUND("TICKET_NOT_FOUND", 404),
    INVALID_TICKET("INVALID_TICKET", 400),
    CONFIRMATION_EXPIRED("CONFIRMATION_EXPIRED", 408);

    private final String type;
    private final int code;

    TicketExceptionCode(String type, int code) {
        this.type = type;
        this.code = code;
    }
    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
