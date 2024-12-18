package graph.railgo.domain.train.repository;

import graph.railgo.domain.train.model.schedule.TrainSchedule;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainScheduleRepository {
    void save(TrainSchedule s);
    List<TrainSchedule> findConflictingSchedules(String stationId,
                                                 LocalDateTime startTime, LocalDateTime endTime);
}
