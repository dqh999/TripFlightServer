package com.airline.application.ticket.dataTransferObject.request;



public class TicketBookingRequest {
    private String FlightScheduleId;
    private String startairlineId;
    private String endairlineId;
    private Integer childSeats = 0;
    private Integer adultSeats = 0;
    private Integer seniorSeats = 0;

    public TicketBookingRequest() {
    }

    public TicketBookingRequest(String FlightScheduleId, String startairlineId, String endairlineId, Integer childSeats, Integer adultSeats, Integer seniorSeats) {
        this.FlightScheduleId = FlightScheduleId;
        this.startairlineId = startairlineId;
        this.endairlineId = endairlineId;
        this.childSeats = childSeats;
        this.adultSeats = adultSeats;
        this.seniorSeats = seniorSeats;
    }

    public String getFlightScheduleId() {
        return FlightScheduleId;
    }

    public void setFlightScheduleId(String FlightScheduleId) {
        this.FlightScheduleId = FlightScheduleId;
    }

    public String getStartairlineId() {
        return startairlineId;
    }

    public void setStartairlineId(String startairlineId) {
        this.startairlineId = startairlineId;
    }

    public String getEndairlineId() {
        return endairlineId;
    }

    public void setEndairlineId(String endairlineId) {
        this.endairlineId = endairlineId;
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

}
