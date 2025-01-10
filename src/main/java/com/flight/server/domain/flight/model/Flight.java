package com.flight.server.domain.flight.model;

import com.flight.server.domain.flight.type.FlightStatus;
import com.flight.server.domain.utils.model.BaseModel;
import com.flight.server.domain.utils.valueObject.Id;
import com.flight.server.domain.utils.valueObject.Money;

import java.time.LocalDateTime;


public class Flight extends BaseModel {
    private Id id;
    private Id flightId;
    private String code;
    private Id departureAirportId;
    private LocalDateTime departureTime;
    private Id arrivalAirportId;
    private LocalDateTime arrivalTime;
    private String description;
    private Money standardPrice;
    private Integer totalSeats;
    private Integer availableSeats;
    private FlightStatus status;
    private String version;

    public Flight() {
        this.id = new Id();
    }

    public Flight(
            String id,
            String flightId,
            String code,
            String departureAirportId, LocalDateTime departureTime,
            String arrivalAirportId, LocalDateTime arrivalTime,
            String description,
            Money standardPrice,
            Integer totalSeats, Integer availableSeats,
            String status,
            String version
    ) {
        this.id = new Id(id);
        this.flightId = new Id(flightId);
        this.code = code;
        this.departureAirportId = new Id(departureAirportId);
        this.departureTime = departureTime;
        this.arrivalAirportId = new Id(arrivalAirportId);
        this.arrivalTime = arrivalTime;
        this.description = description;
        this.standardPrice = standardPrice;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.status = FlightStatus.valueOf(status);
        this.version =version;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getFlightId() {
        return flightId.getValue();
    }

    public void setFlightId(String flightId) {
        this.flightId = new Id(flightId);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDepartureAirportId() {
        return departureAirportId.getValue();
    }

    public void setDepartureAirportId(String departureAirportId) {
        this.departureAirportId = new Id(departureAirportId);
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalAirportId() {
        return arrivalAirportId.getValue();
    }

    public void setArrivalAirportId(String arrivalAirportId) {
        this.arrivalAirportId = new Id(arrivalAirportId);
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
        return status.toString();
    }

    public void setStatus(String status) {
        this.status = FlightStatus.valueOf(status);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
