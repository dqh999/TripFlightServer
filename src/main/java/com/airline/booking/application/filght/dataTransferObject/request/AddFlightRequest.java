package com.airline.booking.application.filght.dataTransferObject.request;

import com.airline.booking.domain.utils.valueObject.Money;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class AddFlightRequest {
    @NotBlank(message = "Airline ID cannot be blank")
    private String airlineId;
    @NotBlank(message = "Flight code cannot be blank")
    @Size(min = 3, max = 10, message = "Flight code must be between 3 and 10 characters")
    private String code;
    @NotBlank(message = "Departure airport code cannot be blank")
    private String departureAirportCode;
    @NotBlank(message = "Arrival airport code cannot be blank")
    private String arrivalAirportCode;
    @NotNull(message = "Departure time cannot be null")
    private LocalDateTime departureTime;
    @NotNull(message = "Arrival time cannot be null")
    private LocalDateTime arrivalTime;
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
    @NotNull(message = "Standard price cannot be null")
    private Money standardPrice;
    @NotNull(message = "Total seats cannot be null")
    @Min(value = 1, message = "Total seats must be greater than 0")
    private Integer totalSeats;

    public AddFlightRequest() {
    }

    public AddFlightRequest(String airlineId, String code, String departureAirportCode, String arrivalAirportCode, LocalDateTime departureTime, LocalDateTime arrivalTime, String description, Money standardPrice, Integer totalSeats) {
        this.airlineId = airlineId;
        this.code = code;
        this.departureAirportCode = departureAirportCode;
        this.arrivalAirportCode = arrivalAirportCode;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.description = description;
        this.standardPrice = standardPrice;
        this.totalSeats = totalSeats;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Money getStandardPrice() {
        return this.standardPrice;
    }

    public void setStandardPrice(Money standardPrice) {
        this.standardPrice = standardPrice;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }
}
