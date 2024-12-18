package graph.railgo.infrastructure.persistence.train.model.schedule;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_train_schedule_routes")
public class TrainScheduleRouteEntity {
    @Id
    private String id;
    private String scheduleId;
    private String stationId;
    private LocalDateTime arrivalTime;
}
