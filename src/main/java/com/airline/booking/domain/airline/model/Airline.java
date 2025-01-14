package com.airline.booking.domain.airline.model;


import com.airline.booking.domain.airline.type.AirlineStatus;
import com.airline.booking.domain.utils.valueObject.Id;

public class Airline {
    private Id id;
    private String name;
    private String country;
    private String logoUrl;
    private AirlineStatus status;

    public Airline(String id, String name, String country, String logoUrl, String status) {
        this.id = new Id(id);
        this.name = name;
        this.country = country;
        this.logoUrl = logoUrl;
        this.status = AirlineStatus.valueOf(status);
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status = AirlineStatus.valueOf(status);
    }
}
