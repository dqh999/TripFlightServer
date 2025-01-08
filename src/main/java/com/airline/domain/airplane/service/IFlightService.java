package com.airline.domain.airplane.service;


import com.airline.domain.airplane.model.Airplane;

public interface IFlightService {
    Airplane addFlight(Airplane newFlight);
    Airplane getFlightById(String id);
}
