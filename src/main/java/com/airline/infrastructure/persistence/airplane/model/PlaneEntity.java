package com.airline.infrastructure.persistence.airplane.model;

import com.airline.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_Flights")
public class PlaneEntity extends BaseEntity {
    @Id
    private String id;
    private String type;
    private String name;
    @Column(name = "year_manufactured")
    private Integer yearManufactured;
    @Column(name = "speed_limit")
    private Double speedLimit;
    @Column(name = "total_seats")
    private Integer totalSeats;
    private String status;

    public PlaneEntity() {
    }

    public PlaneEntity(String id, String type, String name, Integer yearManufactured, Double speedLimit, Integer totalSeats, String status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.yearManufactured = yearManufactured;
        this.speedLimit = speedLimit;
        this.totalSeats = totalSeats;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return speedLimit;
    }

    public void setSpeedLimit(Double speedLimit) {
        this.speedLimit = speedLimit;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
