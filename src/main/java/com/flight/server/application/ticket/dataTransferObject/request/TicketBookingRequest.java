package com.flight.server.application.ticket.dataTransferObject.request;



public class TicketBookingRequest {
    private String flightId;
    private Integer childSeats = 0;
    private Integer adultSeats = 1;

    public TicketBookingRequest() {
    }

    public TicketBookingRequest(String flightId, Integer childSeats, Integer adultSeats) {
        this.flightId = flightId;
        this.childSeats = childSeats;
        this.adultSeats = adultSeats;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
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

}
