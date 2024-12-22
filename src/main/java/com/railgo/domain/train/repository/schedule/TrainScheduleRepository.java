package com.railgo.domain.train.repository.schedule;

import com.railgo.domain.train.model.schedule.TrainSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TrainScheduleRepository {
    void save(TrainSchedule s);

    Optional<TrainSchedule> findById(String id);
    Page<TrainSchedule> findAllSchedules(String departureStationId, String arrivalStationId,
                                         LocalDateTime startDate,LocalDateTime endDate,
                                         Pageable pageable);

    boolean checkConflictingScheduleAtStation(String stationId, LocalDateTime startTime, LocalDateTime endTime);
    boolean checkConflictingSchedules(String trainId, LocalDateTime departureTime);
}
