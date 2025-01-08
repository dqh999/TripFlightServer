package com.airline.infrastructure.persistence.airplane.repository.impl;

import com.airline.domain.airplane.model.Flight;
import com.airline.domain.airplane.repository.FlightScheduleRepository;
import com.airline.infrastructure.persistence.airplane.mapper.FlightScheduleEntityMapper;
import com.airline.infrastructure.persistence.airplane.model.FlightEntity;
import com.airline.infrastructure.persistence.airplane.repository.FlightScheduleEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class FlightScheduleRepositoryImpl implements FlightScheduleRepository {
    private final FlightScheduleEntityRepository repository;
    private final FlightScheduleEntityMapper mapper;

    @Autowired
    public FlightScheduleRepositoryImpl(FlightScheduleEntityRepository repository,
                                       FlightScheduleEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(Flight s) {
        FlightEntity entity = mapper.toEntity(s);
        repository.save(entity);
    }

    @Override
    public Optional<Flight> findById(String id) {
        return repository.findById(id).map(mapper::toDTO);
    }


    @Override
    public Page<Flight> findAllSchedules(String departureAirportId, String arrivalAirportId,
                                         LocalDateTime startDate, LocalDateTime endDate,
                                         Pageable pageable) {
        return repository.findAllSchedules(departureAirportId,arrivalAirportId,startDate,endDate,pageable)
                .map(mapper::toDTO);
    }
}
