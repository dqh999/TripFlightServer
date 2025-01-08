package com.airline.domain.airport.service.impl;

import com.airline.domain.airline.service.IairlineService;
import com.airline.domain.airport.exception.AirportExceptionCode;
import com.airline.domain.airport.model.Airport;
import com.airline.domain.airport.repository.AirportRepository;
import com.airline.domain.airport.service.IAirportService;
import com.airline.domain.utils.exception.BusinessException;
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
    public Airport addAirport(Airport airline) {
        airportRepository.save(airline);
        return airline;
    }

    @Override
    public Airport getAirport(String id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> new BusinessException(AirportExceptionCode.AIR_PORT_NOT_FOUND));
    }

    @Override
    public Page<Airport> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return airportRepository.search(keyword, pageable);
    }
}
