package com.railgo.application.train.exception;

import com.railgo.domain.utils.exception.ExceptionCode;

public enum TrainExceptionCode  implements ExceptionCode {
    TRAIN_NOT_FOUND("TRAIN_NOT_FOUND", 404);

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
