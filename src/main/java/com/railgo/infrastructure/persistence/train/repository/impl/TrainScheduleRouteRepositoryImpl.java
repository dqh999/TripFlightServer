package com.railgo.infrastructure.persistence.train.repository.impl;

import com.railgo.domain.train.repository.schedule.TrainScheduleRouteRepository;
import com.railgo.infrastructure.persistence.train.repository.TrainScheduleRouteEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class TrainScheduleRouteRepositoryImpl implements TrainScheduleRouteRepository {
    private final TrainScheduleRouteEntityRepository trainScheduleRouteEntityRepository;

    @Autowired
    public TrainScheduleRouteRepositoryImpl(TrainScheduleRouteEntityRepository trainScheduleRouteEntityRepository) {
        this.trainScheduleRouteEntityRepository = trainScheduleRouteEntityRepository;
    }

    @Override
    public boolean checkConflictingScheduleAtStation(String stationId, LocalDateTime startTime, LocalDateTime endTime) {
        return trainScheduleRouteEntityRepository.checkConflictingScheduleAtStation(stationId, startTime, endTime);
    }
}
