package com.railgo.application.train.dataTransferObject.response;



public class TrainResponse {
    private String id;
    private String type;
    private String name;
    private Integer yearManufactured;
    private Integer speedLimit;
    private Integer totalSeats;
    private String status;

    public TrainResponse() {
    }

    public TrainResponse(String id, String type, String name, Integer yearManufactured, Integer speedLimit, Integer totalSeats, String status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.yearManufactured = yearManufactured;
        this.speedLimit = speedLimit;
        this.totalSeats = totalSeats;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
