package com.flight.server.infrastructure.persistence.flight.model;

import com.flight.server.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_flights")
public class FlightEntity extends BaseEntity {
    @Id
    private String id;
    @Column(name = "airline_id")
    private String airlineId;
    @Column(name = "departure_airport_id")
    private String departureAirportId;
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    @Column(name = "arrival_airport_id")
    private String arrivalAirportId;
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;
    private String description;
    private String status;

    public FlightEntity() {
    }

    public FlightEntity(String id, String airlineId, String departureAirportId, LocalDateTime departureTime, String arrivalAirportId, LocalDateTime arrivalTime, String description, String status) {
        this.id = id;
        this.airlineId = airlineId;
        this.departureAirportId = departureAirportId;
        this.departureTime = departureTime;
        this.arrivalAirportId = arrivalAirportId;
        this.arrivalTime = arrivalTime;
        this.description = description;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalAirportId() {
        return arrivalAirportId;
    }

    public void setArrivalAirportId(String arrivalAirportId) {
        this.arrivalAirportId = arrivalAirportId;
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
