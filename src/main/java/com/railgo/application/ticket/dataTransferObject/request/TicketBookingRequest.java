package com.railgo.application.ticket.dataTransferObject.request;



public class TicketBookingRequest {
    private String trainScheduleId;
    private String startStationId;
    private String endStationId;
    private Integer childSeats = 0;
    private Integer adultSeats = 0;
    private Integer seniorSeats = 0;

    public TicketBookingRequest() {
    }

    public TicketBookingRequest(String trainScheduleId, String startStationId, String endStationId, Integer childSeats, Integer adultSeats, Integer seniorSeats) {
        this.trainScheduleId = trainScheduleId;
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.childSeats = childSeats;
        this.adultSeats = adultSeats;
        this.seniorSeats = seniorSeats;
    }

    public String getTrainScheduleId() {
        return trainScheduleId;
    }

    public void setTrainScheduleId(String trainScheduleId) {
        this.trainScheduleId = trainScheduleId;
    }

    public String getStartStationId() {
        return startStationId;
    }

    public void setStartStationId(String startStationId) {
        this.startStationId = startStationId;
    }

    public String getEndStationId() {
        return endStationId;
    }

    public void setEndStationId(String endStationId) {
        this.endStationId = endStationId;
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
