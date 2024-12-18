package graph.railgo.infrastructure.persistence.train.model.schedule;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_train_schedules")
public class TrainScheduleEntity {
    @Id
    private String id;
    private String creatorId;
    private String trainId;
    private String departureStationId;
    private String arrivalStationId;
    private String departureTime;
    private String arrivalTime;
    private Integer totalStops;
    private String status;

    public TrainScheduleEntity() {}
}
