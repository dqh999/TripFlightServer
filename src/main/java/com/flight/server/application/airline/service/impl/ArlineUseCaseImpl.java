package com.flight.server.application.airline.service.impl;

import com.flight.server.application.airline.dataTransferObject.request.AddAirlineRequest;
import com.flight.server.application.airline.dataTransferObject.response.AirlineResponse;
import com.flight.server.application.airline.mapper.AirlineMapper;
import com.flight.server.application.airline.service.IAirlineUseCase;
import com.flight.server.domain.airline.model.Airline;
import com.flight.server.domain.airline.service.IAirlineService;
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
}
