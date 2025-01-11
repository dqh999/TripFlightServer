package com.flight.server.infrastructure.persistence.airport.repository.impl;

import com.flight.server.domain.airport.model.Airport;
import com.flight.server.domain.airport.repository.AirportRepository;
import com.flight.server.infrastructure.persistence.airport.mapper.AirportEntityMapper;
import com.flight.server.infrastructure.persistence.airport.model.AirportEntity;
import com.flight.server.infrastructure.persistence.airport.repository.AirportEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class AirportRepositoryImpl implements AirportRepository {
    private final AirportEntityRepository repository;
    private final AirportEntityMapper mapper;

    @Autowired
    public AirportRepositoryImpl(AirportEntityRepository repository,
                                 AirportEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Airport save(Airport s) {
        AirportEntity entity = mapper.toEntity(s);
        AirportEntity saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public Optional<Airport> findByCode(String code) {
        return repository.findByCode(code).map(mapper::toDTO);
    }

    @Override
    public Optional<Airport> findById(String id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }

    @Override
    public Page<Airport> search(String keyword, Pageable pageable) {
        return repository.searchByKeyword(keyword, pageable).map(mapper::toDTO);
    }
}
