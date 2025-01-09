package com.flight.server.domain.airplane.service.impl;

import com.flight.server.domain.airplane.model.AirplaneSeatClass;
import com.flight.server.domain.airplane.repository.AirplaneSeatClassRepository;
import com.flight.server.domain.airplane.service.IAirplaneSeatClassService;
import com.flight.server.domain.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class AirplaneSeatClassService implements IAirplaneSeatClassService {
    private final AirplaneSeatClassRepository repository;
    public AirplaneSeatClassService(AirplaneSeatClassRepository repository) {
        this.repository = repository;
    }

    @Override
    public AirplaneSeatClass create(AirplaneSeatClass airplaneSeatClass) {
        return repository.save(airplaneSeatClass);
    }

    @Override
    public AirplaneSeatClass update(AirplaneSeatClass airplaneSeatClass) {
        return repository.save(airplaneSeatClass);
    }

    @Override
    public AirplaneSeatClass getById(String id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException("AirplaneSeatClass not found"));
    }

    @Override
    public void delete(AirplaneSeatClass airplaneSeatClass) {

    }
}
