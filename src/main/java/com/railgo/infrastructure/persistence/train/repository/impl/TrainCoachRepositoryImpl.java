package com.railgo.infrastructure.persistence.train.repository.impl;

import com.railgo.domain.train.model.coach.TrainCoach;
import com.railgo.domain.train.repository.TrainCoachRepository;
import com.railgo.infrastructure.persistence.train.mapper.TrainCoachEntityMapper;
import com.railgo.infrastructure.persistence.train.model.coach.TrainCoachEntity;
import com.railgo.infrastructure.persistence.train.repository.TrainCoachEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrainCoachRepositoryImpl implements TrainCoachRepository {
    private final TrainCoachEntityRepository repository;
    private final TrainCoachEntityMapper mapper;

    @Autowired
    public TrainCoachRepositoryImpl(TrainCoachEntityRepository repository, TrainCoachEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(TrainCoach trainCoach) {
        TrainCoachEntity entity = mapper.toEntity(trainCoach);
        repository.save(entity);
    }
}
