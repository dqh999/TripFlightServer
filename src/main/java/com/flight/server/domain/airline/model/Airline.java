package com.flight.server.domain.airline.model;


import com.flight.server.domain.airline.type.AirlineStatus;
import com.flight.server.domain.utils.valueObject.Email;
import com.flight.server.domain.utils.valueObject.Id;

public class Airline {
    private Id id;
    private String name;
    private String country;
    private String phoneNumber;
    private Email email;
    private String website;
    private String logoUrl;
    private AirlineStatus status;

    public Airline(String id, String name, String country, String phoneNumber, String email, String website, String logoUrl, String status) {
        this.id = new Id(id);
        this.name = name;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.email = new Email(email);
        this.website = website;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email.getValue();
    }

    public void setEmail(String email) {
        this.email = new Email(email);
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

    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status = AirlineStatus.valueOf(status);
    }
}
