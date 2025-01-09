package com.flight.server.domain.airline.model;


import com.flight.server.domain.airline.type.AirlineStatus;
import com.flight.server.domain.utils.valueObject.Id;

public class Airline {
    private Id id;
    private String name;
    private String country;
    private String phoneNumber;
    private String email;
    private String website;
    private String logoUrl;
    private AirlineStatus status;

    public Airline(String id, String name, String country, String phoneNumber, String email, String website, String logoUrl, int status) {
        this.id = new Id(id);
        this.name = name;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.website = website;
        this.logoUrl = logoUrl;
        this.status = AirlineStatus.fromValue(status);
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getStatus() {
        return status.getValue();
    }

    public void setStatus(int status) {
        this.status = AirlineStatus.fromValue(status);
    }
}
