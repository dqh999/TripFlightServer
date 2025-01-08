package com.airline.domain.airplane.service;

import com.airline.domain.airplane.model.Flight;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface IFlightScheduleService {
    Flight addSchedule(Flight Flight);

    Flight getSchedule(String id);
    Page<Flight> getAllSchedules(String departureAirportId, String arrivalAirportId,
                                 LocalDateTime departureTime,
                                 int pageNumber, int pageSize);
}
