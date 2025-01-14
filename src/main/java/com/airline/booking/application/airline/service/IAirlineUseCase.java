package com.airline.booking.application.airline.service;


import com.airline.booking.application.airline.dataTransferObject.request.AddAirlineRequest;
import com.airline.booking.application.airline.dataTransferObject.response.AirlineResponse;

public interface IAirlineUseCase {
    AirlineResponse add(AddAirlineRequest request);
    AirlineResponse get(String id);
}
