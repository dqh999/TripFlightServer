package com.railgo.domain.train.model.schedule;

import com.railgo.domain.train.model.Train;
import com.railgo.domain.train.type.TrainScheduleStatus;
import com.railgo.domain.utils.model.BaseModel;
import com.railgo.domain.utils.valueObject.Id;
import com.railgo.domain.utils.valueObject.Money;

import java.util.List;

public class TrainSchedule extends BaseModel {
    private Id id;
    private Train train;
    private Integer totalStops;
    private List<TrainScheduleStop> stops;
    private TrainScheduleStatus status;

    public TrainSchedule() {
        this.id = new Id();
    }


    public TrainSchedule(String id, Train train, Integer totalStops, List<TrainScheduleStop> stops, String status) {
        this.id = new Id(id);
        this.train = train;
        this.totalStops = totalStops;
        this.stops = stops;
        this.status = TrainScheduleStatus.valueOf(status);
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Integer getTotalStops() {
        return totalStops;
    }

    public void setTotalStops(Integer totalStops) {
        this.totalStops = totalStops;
    }

    public List<TrainScheduleStop> getStops() {
        return stops;
    }

    public void setStops(List<TrainScheduleStop> stops) {
        this.stops = stops;
    }

    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status = TrainScheduleStatus.valueOf(status);
    }
}
