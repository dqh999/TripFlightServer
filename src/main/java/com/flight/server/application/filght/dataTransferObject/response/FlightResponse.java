package com.flight.server.application.filght.dataTransferObject.response;

import com.flight.server.application.airline.dataTransferObject.response.AirlineResponse;
import com.flight.server.domain.utils.valueObject.Money;

import java.time.LocalDateTime;

public class FlightResponse {
    private String id;
    private AirlineResponse airline;
    private String code;
    private String departureAirportCode;
    private LocalDateTime departureTime;
    private String arrivalAirportCode;
    private LocalDateTime arrivalTime;
    private Money standardPrice;

    public FlightResponse() {
    }

    public FlightResponse(
            String id,
            AirlineResponse airline,
            String code,
            String departureAirportCode, LocalDateTime departureTime,
            String arrivalAirportCode, LocalDateTime arrivalTime,
            Money standardPrice
    ) {
        this.id = id;
        this.airline = airline;
        this.code = code;
        this.departureAirportCode = departureAirportCode;
        this.departureTime = departureTime;
        this.arrivalAirportCode = arrivalAirportCode;
        this.arrivalTime = arrivalTime;
        this.standardPrice = standardPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AirlineResponse getAirline() {
        return airline;
    }

    public void setAirline(AirlineResponse airline) {
        this.airline = airline;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }


    public Money getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(Money standardPrice) {
        this.standardPrice = standardPrice;
    }

}
