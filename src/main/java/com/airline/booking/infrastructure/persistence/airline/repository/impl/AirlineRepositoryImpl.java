package com.airline.booking.infrastructure.persistence.airline.repository.impl;

import com.airline.booking.domain.airline.model.Airline;
import com.airline.booking.domain.airline.repository.AirlineRepository;
import com.airline.booking.infrastructure.persistence.airline.mapper.AirlineEntityMapper;
import com.airline.booking.infrastructure.persistence.airline.model.AirlineEntity;
import com.airline.booking.infrastructure.persistence.airline.repository.AirlineEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AirlineRepositoryImpl implements AirlineRepository {
    private final AirlineEntityRepository repository;
    private final AirlineEntityMapper mapper;

    public AirlineRepositoryImpl(AirlineEntityRepository repository, AirlineEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Airline save(Airline a) {
        AirlineEntity entity = mapper.toEntity(a);
        AirlineEntity saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public Optional<Airline> findById(String id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
