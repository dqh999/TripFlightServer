package com.railgo.application.station.dataTransferObject.response;


public class StationRouteResponse {
    private String id;
    private String type;
    private String stationAId;
    private String stationBId;
    private Double speedLimit;
    private Double distanceKm;
    private String status;

    public StationRouteResponse() {
    }

    public StationRouteResponse(String id, String type, String stationAId, String stationBId, Double speedLimit, Double distanceKm, String status) {
        this.id = id;
        this.type = type;
        this.stationAId = stationAId;
        this.stationBId = stationBId;
        this.speedLimit = speedLimit;
        this.distanceKm = distanceKm;
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

    public String getStationAId() {
        return stationAId;
    }

    public void setStationAId(String stationBId) {
        this.stationAId = stationBId;
    }

    public String getStationBId() {
        return stationBId;
    }

    public void setEndStationId(String stationBId) {
        this.stationBId = stationBId;
    }

    public Double getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(Double speedLimit) {
        this.speedLimit = speedLimit;
    }

    public Double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
