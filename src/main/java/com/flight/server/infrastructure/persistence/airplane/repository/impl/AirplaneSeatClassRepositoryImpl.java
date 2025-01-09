package com.flight.server.infrastructure.persistence.airplane.repository.impl;

import com.flight.server.domain.airplane.model.AirplaneSeatClass;
import com.flight.server.domain.airplane.repository.AirplaneSeatClassRepository;
import com.flight.server.infrastructure.persistence.airplane.mapper.AirplaneSeatClassMapper;
import com.flight.server.infrastructure.persistence.airplane.model.AirplaneSeatClassEntity;
import com.flight.server.infrastructure.persistence.airplane.repository.AirplaneSeatClassEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AirplaneSeatClassRepositoryImpl implements AirplaneSeatClassRepository {
    private final AirplaneSeatClassEntityRepository repository;
    private final AirplaneSeatClassMapper mapper;

    public AirplaneSeatClassRepositoryImpl(AirplaneSeatClassEntityRepository repository, AirplaneSeatClassMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AirplaneSeatClass save(AirplaneSeatClass t) {
        AirplaneSeatClassEntity entity = mapper.toEntity(t);
        entity = repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Override
    public Optional<AirplaneSeatClass> findById(String id) {
        return repository.findById(id).map(mapper::toDTO);
    }
}
