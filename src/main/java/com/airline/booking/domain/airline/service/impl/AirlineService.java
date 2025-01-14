package com.airline.booking.domain.airline.service.impl;

import com.airline.booking.domain.airline.model.Airline;
import com.airline.booking.domain.airline.repository.AirlineRepository;
import com.airline.booking.domain.airline.service.IAirlineService;
import com.airline.booking.domain.utils.exception.BusinessException;
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

    @Override
    public void checkAirlineActive(String airlineId) {
        if (!repository.existsById(airlineId)) {
            throw new BusinessException();
        }
    }
}
