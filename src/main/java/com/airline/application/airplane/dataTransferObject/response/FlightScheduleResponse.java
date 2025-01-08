package com.airline.application.airplane.dataTransferObject.response;


import com.airline.application.airport.dataTransferObject.response.AirportResponse;
import com.airline.domain.utils.valueObject.Money;

import java.time.LocalDateTime;

public class FlightScheduleResponse {
    private String id;
    private FlightResponse Flight;
    private AirportResponse departureAirport;
    private AirportResponse arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer totalSeats;
    private Money ticketPrice;
    private String status;

    public FlightScheduleResponse() {
    }

    public FlightScheduleResponse(String id, FlightResponse flight, AirportResponse departureAirport, AirportResponse arrivalAirport, LocalDateTime departureTime, LocalDateTime arrivalTime, Integer totalSeats, Money ticketPrice, String status) {
        this.id = id;
        Flight = flight;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.ticketPrice = ticketPrice;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FlightResponse getFlight() {
        return Flight;
    }

    public void setFlight(FlightResponse flight) {
        Flight = flight;
    }

    public AirportResponse getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(AirportResponse departureAirport) {
        this.departureAirport = departureAirport;
    }

    public AirportResponse getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(AirportResponse arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Money getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Money ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
