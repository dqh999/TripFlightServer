package com.railgo.domain.train.exception;

import com.railgo.domain.utils.exception.ExceptionCode;

public enum TrainExceptionCode implements ExceptionCode {
    TRAIN_NOT_FOUND("TRAIN_NOT_FOUND", 404),
    TRAIN_ALREADY_EXISTS("TRAIN_ALREADY_EXISTS", 409),

    TRAIN_SCHEDULE_NOT_FOUND("TRAIN_SCHEDULE_NOT_FOUND", 404),
    TRAIN_SCHEDULE_CONFLICT("TRAIN_SCHEDULE_CONFLICT", 409),
    TRAIN_SCHEDULE_INVALID("TRAIN_SCHEDULE_INVALID", 400),

    TRAIN_INVALID_STATUS("TRAIN_INVALID_STATUS", 400),
    TRAIN_NOT_AVAILABLE("TRAIN_NOT_AVAILABLE", 404);

    private final String type;
    private final int code;

    TrainExceptionCode(String type, int code) {
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
