package com.flight.server.domain.airline.service.impl;

import com.flight.server.domain.airline.model.Airline;
import com.flight.server.domain.airline.repository.AirlineRepository;
import com.flight.server.domain.airline.service.IAirlineService;
import org.springframework.stereotype.Service;

@Service
public class AirlineService implements IAirlineService {
    private final AirlineRepository repository;

    public AirlineService(AirlineRepository repository) {
        this.repository = repository;
    }

    @Override
    public Airline create(Airline airline) {
        return repository.save(airline);
    }

    @Override
    public Airline update(Airline airline) {
        return repository.save(airline);
    }

    @Override
    public Airline getById(String id) {
        return repository.findById(id)
                .orElseThrow();
    }

    @Override
    public void delete(Airline airline) {

    }
}
