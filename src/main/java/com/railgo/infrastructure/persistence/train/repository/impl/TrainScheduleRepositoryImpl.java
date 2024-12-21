package com.railgo.infrastructure.persistence.train.repository.impl;

import com.railgo.infrastructure.persistence.train.mapper.TrainScheduleEntityMapper;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.repository.TrainScheduleRepository;
import com.railgo.infrastructure.persistence.train.model.schedule.TrainScheduleEntity;
import com.railgo.infrastructure.persistence.train.repository.TrainScheduleEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TrainScheduleRepositoryImpl implements TrainScheduleRepository {
    private final TrainScheduleEntityRepository repository;
    private final TrainScheduleEntityMapper mapper;

    @Autowired
    public TrainScheduleRepositoryImpl(TrainScheduleEntityRepository repository,
                                       TrainScheduleEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(TrainSchedule s) {
        TrainScheduleEntity entity = mapper.toEntity(s);
        repository.save(entity);
    }
    @Override
    public boolean checkConflictingSchedules(String stationId, LocalDateTime startTime, LocalDateTime endTime){
        return repository.checkConflictingSchedules(stationId,startTime,endTime);
    }
    @Override
    public List<TrainSchedule> findConflictingSchedules(String stationId, LocalDateTime startTime, LocalDateTime endTime) {
        return repository.findConflictingSchedules(stationId,startTime,endTime).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Page<TrainSchedule> findAllSchedules(String departureStationId, String arrivalStationId,
                                                LocalDateTime startDate,LocalDateTime endDate,
                                                Pageable pageable) {
        return repository.findAllSchedules(departureStationId,arrivalStationId,startDate,endDate,pageable)
                .map(mapper::toDTO);
    }
}
