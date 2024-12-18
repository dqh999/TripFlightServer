package graph.railgo.domain.station.model;

import graph.railgo.domain.utils.model.BaseModel;
import graph.railgo.domain.utils.valueObject.Id;

public class StationRoute extends BaseModel {
    private Id id;
    private String type;
    private String startStationId;
    private String endStationId;
    private Double speedLimit;
    private Double distanceKm;
    private Integer durationMinutes;
    private String status;

    public StationRoute(String id, String type, String startStationId, String endStationId, Double speedLimit, Double distanceKm, Integer durationMinutes, String status) {
        this.id = new Id(id);
        this.type = type;
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.speedLimit = speedLimit;
        this.distanceKm = distanceKm;
        this.durationMinutes = durationMinutes;
        this.status = status;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
