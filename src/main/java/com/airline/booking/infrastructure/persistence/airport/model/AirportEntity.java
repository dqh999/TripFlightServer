package com.airline.booking.infrastructure.persistence.airport.model;

import com.airline.booking.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "tbl_airports")
public class AirportEntity extends BaseEntity {
    @Id
    private String id;
    private String code;
    private String name;
    private String country;
    private String city;
    private String status;

    public AirportEntity() {
    }


    public AirportEntity(String id, String code, String name, String country, String city, String status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.country = country;
        this.city = city;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
