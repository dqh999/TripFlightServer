package com.flight.server.domain.flight.model;

import com.flight.server.domain.flight.type.FlightStatus;
import com.flight.server.domain.utils.model.BaseModel;
import com.flight.server.domain.utils.valueObject.Id;

import java.time.LocalDateTime;


public class Flight extends BaseModel {
    private Id id;
    private Id flightId;
    private Id departureAirportId;
    private LocalDateTime departureTime;
    private Id arrivalAirportId;
    private LocalDateTime arrivalTime;
    private String description;
    private FlightStatus status;

    public Flight() {
        this.id = new Id();
    }

    public Flight(String id, String flightId, String departureAirportId, LocalDateTime departureTime, String arrivalAirportId, LocalDateTime arrivalTime, String description, String status) {
        this.id = new Id(id);
        this.flightId = new Id(flightId);
        this.departureAirportId = new Id(departureAirportId);
        this.departureTime = departureTime;
        this.arrivalAirportId = new Id(arrivalAirportId);
        this.arrivalTime = arrivalTime;
        this.description = description;
        this.status = FlightStatus.valueOf(status);
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

    public String getStatus() {
        return status.toString();
    }

    public void setStatus(String status) {
        this.status = FlightStatus.valueOf(status);
    }
}
