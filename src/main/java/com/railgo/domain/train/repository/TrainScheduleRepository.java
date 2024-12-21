package com.railgo.domain.train.repository;

import com.railgo.domain.train.model.schedule.TrainSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainScheduleRepository {
    void save(TrainSchedule s);
    boolean checkConflictingSchedules(String stationId, LocalDateTime startTime, LocalDateTime endTime);
    List<TrainSchedule> findConflictingSchedules(String stationId,
                                                 LocalDateTime startTime, LocalDateTime endTime);

    Page<TrainSchedule> findAllSchedules(String departureStationId, String arrivalStationId,
                                         LocalDateTime startDate,LocalDateTime endDate,
                                         Pageable pageable);
}
