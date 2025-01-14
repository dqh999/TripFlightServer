package com.airline.booking.infrastructure.persistence.airline.model;

import com.airline.booking.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_airlines")
public class AirlineEntity extends BaseEntity {
    @Id
    private String id;
    private String name;
    private String country;
    private String logoUrl;
    private String status;
    public AirlineEntity() {}

    public AirlineEntity(String id, String name, String country, String logoUrl, String status) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.logoUrl = logoUrl;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
