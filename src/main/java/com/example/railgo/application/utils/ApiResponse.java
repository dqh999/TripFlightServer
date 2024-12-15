package com.example.railgo.application.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @JsonIgnore
    private HttpStatus httpStatus = HttpStatus.OK;

    @JsonIgnore
    private HttpHeaders headers;

    private int code = 200;

    private Boolean success = true;

    private String message;

    private T data;

    private Map<String, Object> errors;

    private Date timestamp = new Date();

    public ApiResponse() {
        this.errors = new HashMap<>();
    }

    public static <T> ApiResponse<T> build() {
        return new ApiResponse<>();
    }

    @PostConstruct
    private void init() {
        httpStatus = HttpStatus.OK;
        code = httpStatus.value();
        errors = new HashMap<>();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Object> errors) {
        this.errors = errors;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    // Fluent builder methods
    public ApiResponse<T> withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public ApiResponse<T> withHttpStatus(HttpStatus status) {
        this.httpStatus = status;
        this.code = status.value();
        return this;
    }

    public ApiResponse<T> withCode(int code) {
        this.code = code;
        return this;
    }

    public ApiResponse<T> withData(T data) {
        this.data = data;
        return this;
    }

    public ApiResponse<T> withHttpHeaders(HttpHeaders headers) {
        this.headers = headers;
        return this;
    }

    public ApiResponse<T> withMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiResponse<T> withErrors(Map<String, Object> errors) {
        this.errors = errors;
        return this;
    }
    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
    public ResponseEntity<?> toEntity() {
        if (success) {
            return new ResponseEntity<>(data, headers, httpStatus);
        }
        return new ResponseEntity<>(this, headers, httpStatus);
    }
}
