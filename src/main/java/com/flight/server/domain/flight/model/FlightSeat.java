package com.flight.server.domain.flight.model;

import com.flight.server.domain.utils.model.BaseModel;
import com.flight.server.domain.utils.valueObject.Id;

public class FlightSeat extends BaseModel {
    private Id id;
    private Id flightId;
    private Id classId;
    private int totalSeats;
    private int availableSeats;

    public FlightSeat(String id, String flightId, String classId, int totalSeats, int availableSeats) {
        this.id = new Id(id);
        this.flightId = new Id(flightId);
        this.classId = new Id(classId);
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
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

    public String getClassId() {
        return classId.getValue();
    }

    public void setClassId(String classId) {
        this.classId = new Id(classId);
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
