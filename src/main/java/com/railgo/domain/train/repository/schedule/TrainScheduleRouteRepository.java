package com.railgo.domain.train.repository.schedule;

import java.time.LocalDateTime;

public interface TrainScheduleRouteRepository {
    boolean checkConflictingScheduleAtStation(String stationId, LocalDateTime startTime, LocalDateTime endTime);
}
