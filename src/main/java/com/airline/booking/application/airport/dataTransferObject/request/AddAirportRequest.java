package com.airline.booking.application.airport.dataTransferObject.request;

public class AddAirportRequest {
    private String code;
    private String name;
    private String country;
    private String city;
    private String operatingHours;

    public AddAirportRequest() {
    }

    public AddAirportRequest(String code,String name, String country, String city, String operatingHours) {
       this.code = code;
        this.name = name;
        this.country = country;
        this.city = city;
        this.operatingHours = operatingHours;
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

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }
}
