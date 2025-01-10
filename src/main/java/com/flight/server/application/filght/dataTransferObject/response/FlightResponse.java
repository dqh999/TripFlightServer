package com.flight.server.application.filght.dataTransferObject.response;

import com.flight.server.application.airline.dataTransferObject.response.AirlineResponse;
import com.flight.server.application.airport.dataTransferObject.response.AirportResponse;
import com.flight.server.domain.utils.valueObject.Money;

import java.time.LocalDateTime;

public class FlightResponse {
    private AirlineResponse airline;
    private AirportResponse departureAirport;
    private LocalDateTime departureTime;
    private AirportResponse arrivalAirport;
    private LocalDateTime arrivalTime;
    private int childSeats = 0;
    private int adultSeats = 1;
    private Money standardPrice;
    private Money pricePare;

    public FlightResponse() {
    }

    public FlightResponse(AirlineResponse airline, AirportResponse departureAirport, LocalDateTime departureTime, AirportResponse arrivalAirport, LocalDateTime arrivalTime, int childSeats, int adultSeats, Money standardPrice, Money pricePare) {
        this.airline = airline;
        this.departureAirport = departureAirport;
        this.departureTime = departureTime;
        this.arrivalAirport = arrivalAirport;
        this.arrivalTime = arrivalTime;
        this.childSeats = childSeats;
        this.adultSeats = adultSeats;
        this.standardPrice = standardPrice;
        this.pricePare = pricePare;
    }

    public AirlineResponse getAirline() {
        return airline;
    }

    public void setAirline(AirlineResponse airline) {
        this.airline = airline;
    }

    public AirportResponse getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(AirportResponse departureAirport) {
        this.departureAirport = departureAirport;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public AirportResponse getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(AirportResponse arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getChildSeats() {
        return childSeats;
    }

    public void setChildSeats(int childSeats) {
        this.childSeats = childSeats;
    }

    public int getAdultSeats() {
        return adultSeats;
    }

    public void setAdultSeats(int adultSeats) {
        this.adultSeats = adultSeats;
    }

    public Money getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(Money standardPrice) {
        this.standardPrice = standardPrice;
    }

    public Money getPricePare() {
        return pricePare;
    }

    public void setPricePare(Money pricePare) {
        this.pricePare = pricePare;
    }
}
