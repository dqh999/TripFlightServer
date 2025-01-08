package com.airline.infrastructure.persistence.airplane.repository.impl;

import com.airline.domain.airplane.model.Airplane;
import com.airline.domain.airplane.repository.FlightRepository;
import com.airline.infrastructure.persistence.airplane.mapper.FlightEntityMapper;
import com.airline.infrastructure.persistence.airplane.model.PlaneEntity;
import com.airline.infrastructure.persistence.airplane.repository.FlightEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class FlightRepositoryImpl implements FlightRepository {
    private final FlightEntityRepository repository;
    private final FlightEntityMapper mapper;

    @Autowired
    public FlightRepositoryImpl(FlightEntityRepository repository,
                               FlightEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(Airplane t) {
        PlaneEntity schedule = mapper.toEntity(t);
        repository.save(schedule);
    }

    @Override
    public Optional<Airplane> findById(String id) {
        return repository.findById(id).map(mapper::toDTO);
    }
}
