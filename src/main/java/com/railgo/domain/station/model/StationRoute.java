package com.railgo.domain.station.model;

import com.railgo.domain.station.type.StationRouteStatus;
import com.railgo.domain.station.type.StationRouteType;
import com.railgo.domain.station.valueObject.Distance;
import com.railgo.domain.utils.valueObject.Speed;
import com.railgo.domain.utils.model.BaseModel;
import com.railgo.domain.utils.valueObject.Id;

public class StationRoute extends BaseModel {
    private Id id;
    private StationRouteType type;
    private Station stationA;
    private Station stationB;
    private Speed speedLimit;
    private Distance distanceKm;
    private Double travelTime;
    private StationRouteStatus status;

    public StationRoute() {
        this.id = new Id();
    }

    public StationRoute(String id, StationRouteType type, Station stationA, Station stationB, Double speedLimit, Double distanceKm, Double travelTime, StationRouteStatus status) {
        this.id = new Id(id);
        this.type = type;
        this.stationA = stationA;
        this.stationB = stationB;
        this.speedLimit = new Speed(speedLimit);
        this.distanceKm = new Distance(distanceKm);
        this.travelTime = travelTime;
        this.status = status;
    }
    public StationRoute reverseStation(){
        Station temp1 = stationA;
        this.stationA = this.stationB;
        this.stationB = temp1;
        return this;
    };
    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getType() {
        return type.getValue();
    }

    public void setType(String type) {
        this.type = StationRouteType.valueOf(type);
    }

    public Station getStationA() {
        return stationA;
    }

    public void setStationA(Station stationA) {
        this.stationA = stationA;
    }

    public Station getStationB() {
        return stationB;
    }

    public void setStationB(Station stationB) {
        this.stationB = stationB;
    }

    public Double getSpeedLimit() {
        return speedLimit.getValue();
    }

    public void setSpeedLimit(Double speedLimit) {
        this.speedLimit = new Speed(speedLimit);
    }

    public Double getDistanceKm() {
        return distanceKm.getValue();
    }

    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = new Distance(distanceKm);
    }

    public Double getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Double travelTime) {
        this.travelTime = travelTime;
    }

    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status = StationRouteStatus.valueOf(status);
    }
}
