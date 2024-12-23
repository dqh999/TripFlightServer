package com.railgo.application.ticket.dataTransferObject.response;

import com.railgo.domain.utils.valueObject.Money;

import java.time.LocalDateTime;

public class TicketResponse {
    private String id;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer seatNumber;
    private Money totalPrice;
    private String status;

    public TicketResponse() {}
    public TicketResponse(String id, LocalDateTime departureTime, LocalDateTime arrivalTime, Integer seatNumber, Money totalPrice, String status) {
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatNumber = seatNumber;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Money totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
