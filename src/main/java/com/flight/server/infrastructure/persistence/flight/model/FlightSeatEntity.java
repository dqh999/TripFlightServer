package com.flight.server.infrastructure.persistence.flight.model;

import com.flight.server.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_flight_seats")
public class FlightSeatEntity extends BaseEntity {
    @Id
    private String id;
    @Column(name = "flight_id")
    private String flightId;
    @Column(name = "class_id")
    private String classId;
    @Column(name = "total_seats")
    private String totalSeats;
    @Column(name = "available_seats")
    private String availableSeats;
    public FlightSeatEntity() {}
    public FlightSeatEntity(LocalDateTime createdAt, LocalDateTime updatedAt, String id, String flightId, String classId, String totalSeats, String availableSeats) {
        super(createdAt, updatedAt);
        this.id = id;
        this.flightId = flightId;
        this.classId = classId;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(String totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(String availableSeats) {
        this.availableSeats = availableSeats;
    }
}
