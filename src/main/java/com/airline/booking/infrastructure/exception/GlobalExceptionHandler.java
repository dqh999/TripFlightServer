package com.airline.booking.infrastructure.exception;

import com.airline.booking.domain.exception.BusinessException;
import com.airline.booking.domain.exception.ExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return createErrorResponse(
                400,
                "Validation failed",
                errors
        );
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
        return handleException(e, e.getExceptionCode());
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse<?>> handleApplicationException(ApplicationException e) {
        return handleException(e, e.getExceptionCode());
    }

    private ResponseEntity<ApiResponse<?>> handleException(Exception e, ExceptionCode code) {
        Map<String, Object> errors = createErrorDetails(code);
        return createErrorResponse(
                code != null ? code.getCode() : HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(),
                errors
        );
    }

    private Map<String, Object> createErrorDetails(ExceptionCode code) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("type", code != null ? code.getType() : HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return errors;
    }

    private ResponseEntity<ApiResponse<?>> createErrorResponse(int httpStatus, String message, Map<String, Object> errors) {
        HttpStatus status = HttpStatus.resolve(httpStatus);
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        ApiResponse<?> errorResponse = ApiResponse.<Void>build()
                .withCode(status.value())
                .withErrors(errors)
                .withMessage(message);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
