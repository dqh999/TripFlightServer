package com.airline.infrastructure.persistence.airport.repository.impl;

import com.airline.domain.airline.model.airline;
import com.airline.domain.airline.repository.airlineRepository;
import com.airline.infrastructure.persistence.airport.mapper.AirportEntityMapper;
import com.airline.infrastructure.persistence.airport.model.AirportEntity;
import com.airline.infrastructure.persistence.airport.repository.AirportEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class AirportRepositoryImpl implements airlineRepository {
    private final AirportEntityRepository repository;
    private final AirportEntityMapper mapper;

    @Autowired
    public AirportRepositoryImpl(AirportEntityRepository repository,
                                 AirportEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(airline s) {
        AirportEntity entity = mapper.toEntity(s);
        repository.save(entity);
    }

    @Override
    public Optional<airline> findById(String id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    @Override
    public Page<airline> search(String keyword, Pageable pageable) {
        return repository.searchByKeyword(keyword, pageable).map(mapper::toDTO);
    }
}
