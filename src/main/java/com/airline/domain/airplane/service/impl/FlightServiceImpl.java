package com.airline.domain.airplane.service.impl;

import com.airline.application.Flight.exception.FlightApplicationExceptionCode;
import com.airline.domain.airplane.model.Airplane;
import com.airline.domain.airplane.repository.FlightRepository;
import com.airline.domain.airplane.service.IFlightService;
import com.airline.domain.airplane.type.FlightStatus;
import com.airline.domain.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl implements IFlightService {
    private final FlightRepository flightRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
    @Override
    public Airplane addFlight(Airplane newFlight) {
        newFlight.setStatus(FlightStatus.ACTIVE.getValue());
        flightRepository.save(newFlight);
        return newFlight;
    }

    @Override
    public Airplane getFlightById(String id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new BusinessException(FlightApplicationExceptionCode.Flight_NOT_FOUND));
    }
}
