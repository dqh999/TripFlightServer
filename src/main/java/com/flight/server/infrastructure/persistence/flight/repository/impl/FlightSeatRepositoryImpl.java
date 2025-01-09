package com.flight.server.infrastructure.persistence.flight.repository.impl;

import com.flight.server.infrastructure.persistence.flight.mapper.FlightSeatEntityMapper;
import com.flight.server.infrastructure.persistence.flight.repository.FlightSeatEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FlightSeatRepositoryImpl{
    private final FlightSeatEntityRepository repository;
    private final FlightSeatEntityMapper mapper;

    public FlightSeatRepositoryImpl(FlightSeatEntityRepository repository, FlightSeatEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

}
