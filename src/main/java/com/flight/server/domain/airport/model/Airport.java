package com.flight.server.domain.airport.model;

import com.flight.server.domain.airport.type.AirportStatus;
import com.flight.server.domain.utils.model.BaseModel;
import com.flight.server.domain.utils.valueObject.Id;

public class Airport extends BaseModel {
    private Id id;
    private String code;
    private String name;
    private String country;
    private String city;
    private AirportStatus status;

    public Airport(String id, String code, String name, String country, String city, String status) {
        this.id = new Id(id);
        this.code = code;
        this.name = name;
        this.country = country;
        this.city = city;
        this.status = status != null ? AirportStatus.valueOf(status) : null;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status = status != null ? AirportStatus.valueOf(status) : null;
    }
}
