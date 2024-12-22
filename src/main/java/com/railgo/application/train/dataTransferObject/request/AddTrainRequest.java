package com.railgo.application.train.dataTransferObject.request;



public class AddTrainRequest {
    private String type;
    private String name;
    private Integer yearManufactured;
    private Integer speedLimit;
    private Integer totalSeats;

    public AddTrainRequest() {
    }
    public AddTrainRequest(String type, String name, Integer yearManufactured, Integer speedLimit, Integer totalSeats) {
        this.type = type;
        this.name = name;
        this.yearManufactured = yearManufactured;
        this.speedLimit = speedLimit;
        this.totalSeats = totalSeats;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYearManufactured() {
        return yearManufactured;
    }

    public void setYearManufactured(Integer yearManufactured) {
        this.yearManufactured = yearManufactured;
    }

    public Integer getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(Integer speedLimit) {
        this.speedLimit = speedLimit;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }
}
