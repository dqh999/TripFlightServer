package com.airline.application.airplane.service.impl;

import com.airline.application.airplane.dataTransferObject.request.AddFlightRequest;
import com.airline.application.airplane.dataTransferObject.response.FlightResponse;
import com.airline.application.airplane.mapper.FlightMapper;
import com.airline.application.airplane.service.IAirplaneUseCase;
import com.airline.domain.airplane.model.Airplane;
import com.airline.domain.airplane.service.IFlightService;
import com.airline.infrastructure.security.UserDetail;
import org.springframework.stereotype.Service;

@Service
public class AirplaneUseCase implements IAirplaneUseCase {
    private final IFlightService flightService;
    private final FlightMapper flightMapper;

    public AirplaneUseCase(IFlightService flightService,
                           FlightMapper flightMapper) {
        this.flightService = flightService;
        this.flightMapper = flightMapper;
    }

    @Override
    public FlightResponse addFlight(UserDetail userRequest,
                                    AddFlightRequest request) {
        Airplane flightObject = flightMapper.toFlight(request);
        Airplane newFlight = flightService.addFlight(flightObject);
        return flightMapper.toDTO(newFlight);
    }

}
