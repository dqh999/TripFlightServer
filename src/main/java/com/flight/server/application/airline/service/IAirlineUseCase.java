package com.flight.server.application.airline.service;


import com.flight.server.application.airline.dataTransferObject.request.AddAirlineRequest;
import com.flight.server.application.airline.dataTransferObject.response.AirlineResponse;

public interface IAirlineUseCase {
    AirlineResponse add(AddAirlineRequest request);
    AirlineResponse get(String id);
}
