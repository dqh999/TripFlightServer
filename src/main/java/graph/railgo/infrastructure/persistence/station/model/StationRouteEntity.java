package graph.railgo.infrastructure.persistence.station.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_station_routes")
public class StationRouteEntity {
    @Id
    private String id;
    private String type;
    @Column(name = "start_station_id")
    private String startStationId;
    @Column(name = "end_station_id")
    private String endStationId;
    @Column(name = "speed_limit")
    private Double speedLimit;
    @Column(name = "distance_km")
    private Double distanceKm;
    @Column(name = "duration_minutes")
    private Integer durationMinutes;
    private String status;

    public StationRouteEntity() {
    }

    public StationRouteEntity(String id, String type, String startStationId, String endStationId, Double speedLimit, Double distanceKm, Integer durationMinutes, String status) {
        this.id = id;
        this.type = type;
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.speedLimit = speedLimit;
        this.distanceKm = distanceKm;
        this.durationMinutes = durationMinutes;
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
