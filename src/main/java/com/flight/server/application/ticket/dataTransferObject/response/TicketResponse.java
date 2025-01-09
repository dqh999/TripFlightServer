package com.flight.server.application.ticket.dataTransferObject.response;

import com.airline.application.airline.dataTransferObject.response.airlineResponse;
import com.flight.server.domain.utils.valueObject.Money;

import java.time.LocalDateTime;

public class TicketResponse {
    private String id;
    private String FlightName;
    private airlineResponse departureairline;
    private LocalDateTime departureTime;
    private airlineResponse arrivalairline;
    private LocalDateTime arrivalTime;
    private Integer totalSeats;
    private Integer childSeats = 0;
    private Integer adultSeats = 0;
    private Integer seniorSeats = 0;
    private Money totalPrice;
    private String customerName;
    private String contactEmail;
    private String status;

    public TicketResponse() {}

    public TicketResponse(String id,String FlightName, airlineResponse departureairline, LocalDateTime departureTime, airlineResponse arrivalairline, LocalDateTime arrivalTime,Integer totalSeats, Integer childSeats, Integer adultSeats, Integer seniorSeats, Money totalPrice, String customerName,String contactEmail, String status) {
        this.id = id;
        this.FlightName = FlightName;
        this.departureairline = departureairline;
        this.departureTime = departureTime;
        this.arrivalairline = arrivalairline;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.childSeats = childSeats;
        this.adultSeats = adultSeats;
        this.seniorSeats = seniorSeats;
        this.totalPrice = totalPrice;
        this.customerName = customerName;
        this.contactEmail = contactEmail;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlightName() {
        return FlightName;
    }

    public void setFlightName(String FlightName) {
        this.FlightName = FlightName;
    }

    public airlineResponse getDepartureairline() {
        return departureairline;
    }

    public void setDepartureairline(airlineResponse departureairline) {
        this.departureairline = departureairline;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public airlineResponse getArrivalairline() {
        return arrivalairline;
    }

    public void setArrivalairline(airlineResponse arrivalairline) {
        this.arrivalairline = arrivalairline;
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

    public Integer getChildSeats() {
        return childSeats;
    }

    public void setChildSeats(Integer childSeats) {
        this.childSeats = childSeats;
    }

    public Integer getAdultSeats() {
        return adultSeats;
    }

    public void setAdultSeats(Integer adultSeats) {
        this.adultSeats = adultSeats;
    }

    public Integer getSeniorSeats() {
        return seniorSeats;
    }

    public void setSeniorSeats(Integer seniorSeats) {
        this.seniorSeats = seniorSeats;
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Money totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
