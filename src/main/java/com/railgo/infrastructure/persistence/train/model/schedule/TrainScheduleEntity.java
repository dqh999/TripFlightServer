package com.railgo.infrastructure.persistence.train.model.schedule;

import com.railgo.infrastructure.persistence.train.model.TrainEntity;
import com.railgo.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tbl_train_schedules")
public class TrainScheduleEntity extends BaseEntity {
    @Id
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", referencedColumnName = "id", nullable = false)
    private TrainEntity train;
    @Column(name = "total_stops")
    private Integer totalStops;
    @OneToMany(mappedBy = "scheduleId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("stopOrder ASC")
    private List<TrainScheduleStopEntity> stops;
    private String status;

    public TrainScheduleEntity() {
    }

    public TrainScheduleEntity(String id, TrainEntity train, Integer totalStops, List<TrainScheduleStopEntity> stops, String status) {
        this.id = id;
        this.train = train;
        this.totalStops = totalStops;
        this.stops = stops;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TrainEntity getTrain() {
        return train;
    }

    public void setTrain(TrainEntity train) {
        this.train = train;
    }

    public Integer getTotalStops() {
        return totalStops;
    }

    public void setTotalStops(Integer totalStops) {
        this.totalStops = totalStops;
    }

    public List<TrainScheduleStopEntity> getStops() {
        return stops;
    }

    public void setStops(List<TrainScheduleStopEntity> stops) {
        this.stops = stops;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
