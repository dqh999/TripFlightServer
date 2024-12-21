package com.railgo.infrastructure.persistence.train.repository.impl;

import com.railgo.domain.train.model.Train;
import com.railgo.domain.train.repository.TrainRepository;
import com.railgo.infrastructure.persistence.train.mapper.TrainEntityMapper;
import com.railgo.infrastructure.persistence.train.model.TrainEntity;
import com.railgo.infrastructure.persistence.train.repository.TrainEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrainRepositoryImpl implements TrainRepository {
    private final TrainEntityRepository repository;
    private final TrainEntityMapper mapper;

    @Autowired
    public TrainRepositoryImpl(TrainEntityRepository repository,
                               TrainEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(Train t) {
        TrainEntity schedule = mapper.toEntity(t);
        repository.save(schedule);
    }

    @Override
    public Optional<Train> findById(String id) {
        return repository.findById(id).map(mapper::toDTO);
    }
}
