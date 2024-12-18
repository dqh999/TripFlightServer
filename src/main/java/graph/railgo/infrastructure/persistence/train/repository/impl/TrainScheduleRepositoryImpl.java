package graph.railgo.infrastructure.persistence.train.repository.impl;

import graph.railgo.infrastructure.persistence.train.mapper.TrainScheduleEntityMapper;
import graph.railgo.domain.train.model.schedule.TrainSchedule;
import graph.railgo.domain.train.repository.TrainScheduleRepository;
import graph.railgo.infrastructure.persistence.train.model.schedule.TrainScheduleEntity;
import graph.railgo.infrastructure.persistence.train.repository.TrainScheduleEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TrainScheduleRepositoryImpl implements TrainScheduleRepository {
    private final TrainScheduleEntityRepository repository;
    private final TrainScheduleEntityMapper mapper;

    @Autowired
    public TrainScheduleRepositoryImpl(TrainScheduleEntityRepository repository, TrainScheduleEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(TrainSchedule s) {
        TrainScheduleEntity entity = mapper.toEntity(s);
        repository.save(entity);
    }

    @Override
    public List<TrainSchedule> findConflictingSchedules(String stationId, LocalDateTime startTime, LocalDateTime endTime) {
        return repository.findConflictingSchedules(stationId,startTime,endTime).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
}
