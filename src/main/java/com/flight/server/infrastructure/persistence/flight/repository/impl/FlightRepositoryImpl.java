package com.flight.server.infrastructure.persistence.flight.repository.impl;

import com.flight.server.domain.flight.model.Flight;
import com.flight.server.domain.flight.repository.FlightRepository;
import com.flight.server.infrastructure.persistence.flight.mapper.FlightEntityMapper;
import com.flight.server.infrastructure.persistence.flight.model.FlightEntity;
import com.flight.server.infrastructure.persistence.flight.repository.FlightEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class FlightRepositoryImpl implements FlightRepository {
    private final FlightEntityRepository repository;
    private final FlightEntityMapper mapper;

    @Autowired
    public FlightRepositoryImpl(FlightEntityRepository repository,
                                FlightEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flight save(Flight s) {
        FlightEntity entity = mapper.toEntity(s);
        FlightEntity saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public boolean existByCode(String code) {
        return repository.existsByCode(code);
    }

    @Override
    public Optional<Flight> findById(String id) {
        return repository.findById(id).map(mapper::toDTO);
    }


    @Override
    public Page<Flight> findFlights(
            String departureAirportId, String arrivalAirportId,
            Integer totalSeats,
            LocalDateTime startDate, LocalDateTime endDate,
            Pageable pageable
    ) {
        return repository.findFlights(
                        departureAirportId, arrivalAirportId,
                        totalSeats,
                        startDate, endDate,
                        pageable
                )
                .map(mapper::toDTO);
    }
}
