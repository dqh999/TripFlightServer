package com.airline.booking.application.ticket.dataTransferObject.response;

import com.airline.booking.application.filght.dataTransferObject.response.FlightResponse;
import com.airline.booking.domain.utils.valueObject.Money;

public class TicketResponse {
    private String id;
    private FlightResponse flight;
    private Integer totalSeats;
    private Integer childSeats = 0;
    private Integer adultSeats = 0;
    private Money totalPrice;
    private String customerName;
    private String contactEmail;
    private String status;

    public TicketResponse() {}

    public TicketResponse(String id, FlightResponse flight, Integer totalSeats, Integer childSeats, Integer adultSeats, Money totalPrice, String customerName, String contactEmail, String status) {
        this.id = id;
        this.flight = flight;
        this.totalSeats = totalSeats;
        this.childSeats = childSeats;
        this.adultSeats = adultSeats;
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

    public FlightResponse getFlight() {
        return flight;
    }

    public void setFlight(FlightResponse flight) {
        this.flight = flight;
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
