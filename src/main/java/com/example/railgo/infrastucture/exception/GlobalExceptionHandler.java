package com.example.railgo.infrastucture.exception;

import com.example.railgo.domain.utils.exception.BusinessException;
import com.example.railgo.domain.utils.exception.ExceptionCode;
import com.example.railgo.presentation.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
        ExceptionCode code = e.getExceptionCode();
        Map<String, Object> errors = new HashMap<>();
        errors.put("type", code != null ? code.getType() : HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        return createErrorResponse(
                code != null ? code.getCode() : HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(),
                errors
        );
    }

    private ResponseEntity<ApiResponse<?>> createErrorResponse(int httpStatus, String message, Map<String, Object> errors) {
        ApiResponse<?> errorResponse = ApiResponse.<Void>build()
                .withCode(httpStatus)
                .withErrors(errors)
                .withMessage(message);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
