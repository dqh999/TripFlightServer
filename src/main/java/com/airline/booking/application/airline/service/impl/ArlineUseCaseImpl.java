package com.airline.booking.application.airline.service.impl;

import com.airline.booking.application.airline.dataTransferObject.request.AddAirlineRequest;
import com.airline.booking.application.airline.dataTransferObject.response.AirlineResponse;
import com.airline.booking.application.airline.mapper.AirlineMapper;
import com.airline.booking.application.airline.service.IAirlineUseCase;
import com.airline.booking.domain.airline.model.Airline;
import com.airline.booking.domain.airline.service.IAirlineService;
import org.springframework.stereotype.Service;

@Service
public class ArlineUseCaseImpl implements IAirlineUseCase {
    private final IAirlineService airlineService;
    private final AirlineMapper airlineMapper;
    public ArlineUseCaseImpl(
            IAirlineService airlineService,
            AirlineMapper airlineMapper
    ) {
        this.airlineService = airlineService;
        this.airlineMapper = airlineMapper;
    }

    @Override
    public AirlineResponse add(AddAirlineRequest request) {
        Airline airline = airlineMapper.toAirline(request);
        Airline newAirline = airlineService.create(airline);
        return airlineMapper.toResponse(newAirline);
    }

    @Override
    public AirlineResponse get(String id) {
        Airline existAirline = airlineService.getById(id);
        return airlineMapper.toResponse(existAirline);
    }
}
