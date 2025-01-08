package com.airline.domain.airplane.model;

import com.airline.domain.Flight.type.FlightStatus;
import com.airline.domain.Flight.type.FlightType;
import com.airline.domain.utils.model.BaseModel;
import com.airline.domain.utils.valueObject.Id;
import com.airline.domain.utils.valueObject.Speed;

import java.math.BigDecimal;

public class Airplane extends BaseModel {
    private Id id;
    private FlightType type;
    private String name;
    private Integer yearManufactured;
    private Speed speedLimit;
    private Integer totalSeats;
    private FlightStatus status;

    public Airplane() {
        this.id = new Id();
    }

    public Airplane(String id, FlightType type, String name, Integer yearManufactured, Speed speedLimit, Integer totalSeats, FlightStatus status) {
        this.id = new Id(id);
        this.type = type;
        this.name = name;
        this.yearManufactured = yearManufactured;
        this.speedLimit = speedLimit;
        this.totalSeats = totalSeats;
        this.status = status;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getType() {
        return type.getValue();
    }

    public void setType(String type) {
        this.type = FlightType.valueOf(type);
    }
    public BigDecimal getRatePerKm(){
        return type.getRatePerKm();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYearManufactured() {
        return yearManufactured;
    }

    public void setYearManufactured(Integer yearManufactured) {
        this.yearManufactured = yearManufactured;
    }

    public Double getSpeedLimit() {
        return speedLimit.getValue();
    }

    public void setSpeedLimit(Double speedLimit) {
        this.speedLimit = new Speed(speedLimit);
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status = FlightStatus.valueOf(status);
    }
}
