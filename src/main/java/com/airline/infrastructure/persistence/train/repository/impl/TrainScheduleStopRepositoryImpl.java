package com.railgo.infrastructure.persistence.train.repository.impl;

import com.railgo.domain.train.model.schedule.TrainScheduleStop;
import com.railgo.domain.train.repository.schedule.TrainScheduleStopRepository;
import com.railgo.infrastructure.persistence.train.mapper.TrainScheduleStopEntityMapper;
import com.railgo.infrastructure.persistence.train.model.schedule.TrainScheduleStopEntity;
import com.railgo.infrastructure.persistence.train.repository.TrainScheduleStopEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TrainScheduleStopRepositoryImpl implements TrainScheduleStopRepository {
    private final TrainScheduleStopEntityRepository trainScheduleStopEntityRepository;
    private final TrainScheduleStopEntityMapper trainScheduleStopEntityMapper;

    @Autowired
    public TrainScheduleStopRepositoryImpl(TrainScheduleStopEntityRepository trainScheduleStopEntityRepository,
                                           TrainScheduleStopEntityMapper trainScheduleStopEntityMapper) {
        this.trainScheduleStopEntityRepository = trainScheduleStopEntityRepository;
        this.trainScheduleStopEntityMapper = trainScheduleStopEntityMapper;
    }

    @Override
    public void save(TrainScheduleStop trainScheduleStop) {
        TrainScheduleStopEntity trainScheduleStopEntity = trainScheduleStopEntityMapper.toEntity(trainScheduleStop);
        trainScheduleStopEntityRepository.save(trainScheduleStopEntity);
    }

    @Override
    public void saveAll(List<TrainScheduleStop> trainScheduleStops) {
        List<TrainScheduleStopEntity> trainScheduleStopEntities = trainScheduleStopEntityMapper.toEntities(trainScheduleStops);
        trainScheduleStopEntityRepository.saveAll(trainScheduleStopEntities);
    }

    @Override
    public List<TrainScheduleStop> findByScheduleId(String scheduleId) {
        return trainScheduleStopEntityRepository.findAllByScheduleId(scheduleId)
                .stream().map(trainScheduleStopEntityMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public boolean checkConflictingScheduleAtStation(String stationId, LocalDateTime startTime, LocalDateTime endTime) {
        return trainScheduleStopEntityRepository.checkConflictingScheduleAtStation(stationId, startTime, endTime);
    }
}
