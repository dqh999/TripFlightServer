package graph.railgo.domain.train.model.schedule;

import graph.railgo.domain.utils.valueObject.Id;

import java.time.LocalDateTime;

public class TrainScheduleRoute {
    private Id id;
    private String scheduleId;
    private String stationId;
    private LocalDateTime arrivalTime;
}
