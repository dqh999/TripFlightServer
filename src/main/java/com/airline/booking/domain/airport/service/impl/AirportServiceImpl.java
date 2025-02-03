package com.airline.booking.domain.airport.service.impl;

import com.airline.booking.domain.airport.exception.AirportExceptionCode;
import com.airline.booking.domain.airport.model.Airport;
import com.airline.booking.domain.airport.repository.AirportRepository;
import com.airline.booking.domain.airport.service.IAirportService;
import com.airline.booking.domain.airport.type.AirportStatus;
import com.airline.booking.domain.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public boolean isAirportAvailableAtTime(String airportCode, LocalDateTime time) {
        return airportRepository.existsByCode(airportCode);
    }

    @Override
    public Airport getById(String id) {
        return airportRepository.findById(id)
                .orElseThrow( () -> new BusinessException(AirportExceptionCode.AIR_PORT_NOT_FOUND));
    }

    @Override
    public void delete(Airport airport) {

    }

    @Override
    public Page<Airport> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return airportRepository.search(keyword, pageable);
    }
}
