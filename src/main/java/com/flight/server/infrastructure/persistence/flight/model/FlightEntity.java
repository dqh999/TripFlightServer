package com.flight.server.infrastructure.persistence.flight.model;

import com.flight.server.domain.utils.valueObject.Money;
import com.flight.server.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_flights")
public class FlightEntity extends BaseEntity {
    @Id
    private String id;
    @Column(name = "airline_id")
    private String airlineId;
    private String code;
    @Column(name = "departure_airport_id")
    private String departureAirportId;
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    @Column(name = "arrival_airport_id")
    private String arrivalAirportId;
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;
    private String description;
    @Column(name = "standard_price")
    private BigDecimal standardPrice;
    private String currency;
    @Column(name = "total_seats")
    private Integer totalSeats;
    @Column(name = "available_seats")
    private Integer availableSeats;
    private String status;
    @Version
    private int version;

    public FlightEntity() {
    }

    public FlightEntity(String id, String airlineId, String code, String departureAirportId, LocalDateTime departureTime, String arrivalAirportId, LocalDateTime arrivalTime, Money standardPrice, Integer totalSeats, Integer availableSeats, String description, String status, int version) {
        this.id = id;
        this.airlineId = airlineId;
        this.code = code;
        this.departureAirportId = departureAirportId;
        this.departureTime = departureTime;
        this.arrivalAirportId = arrivalAirportId;
        this.arrivalTime = arrivalTime;
        this.standardPrice = standardPrice.getValue();
        this.currency = standardPrice.getCurrency();
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.description = description;
        this.status = status;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public String getDepartureAirportId() {
        return departureAirportId;
    }

    public void setDepartureAirportId(String departureAirportId) {
        this.departureAirportId = departureAirportId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalAirportId() {
        return arrivalAirportId;
    }

    public void setArrivalAirportId(String arrivalAirportId) {
        this.arrivalAirportId = arrivalAirportId;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Money getStandardPrice() {
        return new Money(this.standardPrice, this.currency);
    }

    public void setStandardPrice(Money standardPrice) {
        this.standardPrice = standardPrice.getValue();
        this.currency = standardPrice.getCurrency();
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}

