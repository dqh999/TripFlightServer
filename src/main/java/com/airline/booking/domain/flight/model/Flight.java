package com.airline.booking.domain.flight.model;

import com.airline.booking.domain.flight.type.FlightStatus;
import com.airline.booking.domain.utils.model.BaseModel;
import com.airline.booking.domain.utils.valueObject.Id;
import com.airline.booking.domain.utils.valueObject.Money;

import java.time.LocalDateTime;


public class Flight extends BaseModel {
    private Id id;
    private Id airlineId;
    private String code;
    private String departureAirportCode;
    private LocalDateTime departureTime;
    private String arrivalAirportCode;
    private LocalDateTime arrivalTime;
    private String description;
    private Money standardPrice;
    private Integer totalSeats;
    private Integer availableSeats;
    private FlightStatus status;
    private Integer version;

    public Flight() {
        this.id = new Id();
    }

    public Flight(
            String id,
            String airlineId,
            String code,
            String departureAirportCode, LocalDateTime departureTime,
            String arrivalAirportCode, LocalDateTime arrivalTime,
            String description,
            Money standardPrice,
            Integer totalSeats, Integer availableSeats,
            String status,
            Integer version
    ) {
        this.id = new Id(id);
        this.airlineId = new Id(airlineId);
        this.code = code;
        this.departureAirportCode = departureAirportCode;
        this.departureTime = departureTime;
        this.arrivalAirportCode = arrivalAirportCode;
        this.arrivalTime = arrivalTime;
        this.description = description;
        this.standardPrice = standardPrice;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.status = status != null ? FlightStatus.valueOf(status) : null;
        this.version =version;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getAirlineId() {
        return airlineId.getValue();
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = new Id(airlineId);
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

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
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
        return standardPrice;
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

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status = FlightStatus.valueOf(status);
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
