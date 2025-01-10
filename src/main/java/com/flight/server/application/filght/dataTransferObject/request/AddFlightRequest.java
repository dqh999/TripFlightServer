package com.flight.server.application.filght.dataTransferObject.request;

import com.flight.server.domain.utils.valueObject.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AddFlightRequest {
    private String airlineId;
    private String departureAirportId;
    private String arrivalAirportId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String description;
    private BigDecimal standardPrice;
    private String currency;
    private Integer totalSeats;
    public AddFlightRequest() {
    }

    public AddFlightRequest(String airlineId, String departureAirportId, String arrivalAirportId, LocalDateTime departureTime, LocalDateTime arrivalTime, String description, Money standardPrice, Integer totalSeats) {
        this.airlineId = airlineId;
        this.departureAirportId = departureAirportId;
        this.arrivalAirportId = arrivalAirportId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.description = description;
        this.standardPrice = standardPrice.getValue();
        this.currency = standardPrice.getCurrency();
        this.totalSeats = totalSeats;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public String getDepartureAirportId() {
        return departureAirportId;
    }

    public void setDepartureAirportId(String departureAirportId) {
        this.departureAirportId = departureAirportId;
    }

    public String getArrivalAirportId() {
        return arrivalAirportId;
    }

    public void setArrivalAirportId(String arrivalAirportId) {
        this.arrivalAirportId = arrivalAirportId;
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
        return new Money(this.standardPrice, this.currency);
    }
    public void setStandardPrice(Money standardPrice) {
        this.standardPrice = standardPrice.getValue();
        this.currency = standardPrice.getCurrency();
    }
    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }
}
