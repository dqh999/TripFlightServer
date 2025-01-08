package com.railgo.domain.train.repository.schedule;

import com.railgo.domain.train.model.schedule.TrainScheduleStop;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainScheduleStopRepository {
    void save(TrainScheduleStop trainScheduleStop);
    void saveAll(List<TrainScheduleStop> trainScheduleStops);
    List<TrainScheduleStop> findByScheduleId(String scheduleId);
    boolean checkConflictingScheduleAtStation(String stationId, LocalDateTime startTime, LocalDateTime endTime);
}
