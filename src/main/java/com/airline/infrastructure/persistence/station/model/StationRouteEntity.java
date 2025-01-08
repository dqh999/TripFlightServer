package com.railgo.infrastructure.persistence.station.model;


import com.railgo.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_station_routes")
public class StationRouteEntity extends BaseEntity {
    @Id
    private String id;
    private String type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "station_a_id", referencedColumnName = "id")
    private StationEntity stationA;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "station_b_id", referencedColumnName = "id")
    private StationEntity stationB;
    @Column(name = "speed_limit")
    private Double speedLimit;
    @Column(name = "distance_km")
    private Double distanceKm;
    private String status;

    public StationRouteEntity() {
    }

    public StationRouteEntity(String id, String type, StationEntity stationA, StationEntity stationB, Double speedLimit, Double distanceKm, String status) {
        this.id = id;
        this.type = type;
        this.stationA = stationA;
        this.stationB = stationB;
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

    public StationEntity getStationA() {
        return stationA;
    }

    public void setStationA(StationEntity stationA) {
        this.stationA = stationA;
    }

    public StationEntity getStationB() {
        return stationB;
    }

    public void setStationB(StationEntity stationB) {
        this.stationB = stationB;
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
