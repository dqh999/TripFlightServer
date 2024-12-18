package graph.railgo.domain.train.model.schedule;

import graph.railgo.domain.train.type.TrainScheduleStatus;
import graph.railgo.domain.utils.valueObject.Id;

public class TrainSchedule {
    private Id id;
    private String creatorId;
    private String trainId;
    private String departureStationId;
    private String arrivalStationId;
    private String departureTime;
    private String arrivalTime;
    private Integer totalStops;
    private TrainScheduleStatus status;

    public TrainSchedule() {
        this.id = new Id();
    }

    public TrainSchedule(String id, String creatorId, String trainId, String departureStationId, String arrivalStationId, String departureTime, String arrivalTime, Integer totalStops, TrainScheduleStatus status) {
        this.id = new Id(id);
        this.creatorId = creatorId;
        this.trainId = trainId;
        this.departureStationId = departureStationId;
        this.arrivalStationId = arrivalStationId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalStops = totalStops;
        this.status = status;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getDepartureStationId() {
        return departureStationId;
    }

    public void setDepartureStationId(String departureStationId) {
        this.departureStationId = departureStationId;
    }

    public String getArrivalStationId() {
        return arrivalStationId;
    }

    public void setArrivalStationId(String arrivalStationId) {
        this.arrivalStationId = arrivalStationId;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getTotalStops() {
        return totalStops;
    }

    public void setTotalStops(Integer totalStops) {
        this.totalStops = totalStops;
    }

    public TrainScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(TrainScheduleStatus status) {
        this.status = status;
    }
}
