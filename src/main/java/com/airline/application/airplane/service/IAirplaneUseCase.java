package com.airline.application.airplane.service;

import com.airline.application.airplane.dataTransferObject.request.AddFlightRequest;
import com.airline.application.airplane.dataTransferObject.response.FlightResponse;
import com.airline.infrastructure.security.UserDetail;

public interface IAirplaneUseCase {
    FlightResponse addFlight(UserDetail userRequest,
                             AddFlightRequest request);
}
