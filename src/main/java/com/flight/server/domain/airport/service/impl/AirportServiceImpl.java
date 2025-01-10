package com.flight.server.domain.airport.service.impl;

import com.flight.server.domain.airport.model.Airport;
import com.flight.server.domain.airport.repository.AirportRepository;
import com.flight.server.domain.airport.service.IAirportService;
import com.flight.server.domain.airport.type.AirportStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AirportServiceImpl implements IAirportService {
    private final AirportRepository airportRepository;

    @Autowired
    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public Airport create(Airport airport) {
        airport.setStatus(AirportStatus.OPERATIONAL.getValue());
        return airportRepository.save(airport);
    }

    @Override
    public Airport update(Airport airport) {
        airportRepository.save(airport);
        return null;
    }

    @Override
    public Airport getById(String id) {
        return airportRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public void delete(Airport airport) {

    }

    @Override
    public Page<Airport> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return airportRepository.search(keyword, pageable);
    }
}
