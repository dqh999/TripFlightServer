package com.flight.server.domain.airport.model;

import com.flight.server.domain.airport.type.AirportStatus;
import com.flight.server.domain.utils.model.BaseModel;
import com.flight.server.domain.utils.valueObject.Email;
import com.flight.server.domain.utils.valueObject.Id;

public class Airport extends BaseModel {
    private Id id;
    private String name;
    private String country;
    private String city;
    private Double latitude;
    private Double longitude;
    private String phoneNumber;
    private Email email;
    private String website;
    private String operatingHours;
    private AirportStatus status;

    public Airport(String id, String name, String country, String city, Double latitude, Double longitude, String phoneNumber, String email, String website, String operatingHours, String status) {
        this.id = new Id(id);
        this.name = name;
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.email = new Email(email);
        this.website = website;
        this.operatingHours = operatingHours;
        this.status = AirportStatus.valueOf(status);
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status = AirportStatus.valueOf(status);
    }
}
