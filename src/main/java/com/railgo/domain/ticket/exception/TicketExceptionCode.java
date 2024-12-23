package com.railgo.domain.ticket.exception;

import com.railgo.domain.utils.exception.ExceptionCode;

public enum TicketExceptionCode implements ExceptionCode {
    TICKET_NOT_FOUND("TICKET_NOT_FOUND", 404),
    INVALID_PASSENGER_AGE("INVALID_PASSENGER_AGE", 400),;


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
