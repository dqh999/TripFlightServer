package com.airline.application.airplane.service.impl;

import com.airline.application.airplane.dataTransferObject.request.AddFlightScheduleRequest;
import com.airline.application.airplane.dataTransferObject.response.FlightScheduleResponse;
import com.airline.application.airplane.mapper.FlightScheduleMapper;
import com.airline.application.airplane.service.IFlightUseCase;
import com.airline.application.utils.PageResponse;
import com.airline.domain.airplane.model.Flight;
import com.airline.domain.airplane.service.IFlightScheduleService;
import com.airline.infrastructure.security.UserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;

@Service

public class FlightUseCase implements IFlightUseCase {
    private static final Logger logger = LoggerFactory.getLogger(FlightUseCase.class);

    private final IFlightScheduleService FlightScheduleService;
    private final FlightScheduleMapper flightScheduleMapper;

    public FlightUseCase(IFlightScheduleService FlightScheduleService,
                         FlightScheduleMapper flightScheduleMapper) {
        this.FlightScheduleService = FlightScheduleService;
        this.flightScheduleMapper = flightScheduleMapper;
    }


    @Override
    public FlightScheduleResponse addSchedule(UserDetail userRequest,
                                                    AddFlightScheduleRequest request) {
        Flight flight = new Flight();
        Flight newFlight = FlightScheduleService.addSchedule(flight);


        return new FlightScheduleResponse();
    }

    @Override
    public PageResponse<FlightScheduleResponse> getAllSchedules(String departureAirportId, String arrivalAirportId,
                                                               LocalDateTime departureTime,
                                                               int childSeats, int adultSeats, int seniorSeats,
                                                               int pageNo, int pageSize, String sortBy) {
        Page<Flight> flightSchedulePage = FlightScheduleService.getAllSchedules(
                departureAirportId, arrivalAirportId,
                departureTime,
                pageNo, pageSize);
        List<FlightScheduleResponse> flightScheduleResponses = flightScheduleMapper.toDTOs(flightSchedulePage.getContent());
        return new PageResponse<>(
                (int) flightSchedulePage.getTotalElements(),
                flightSchedulePage.getTotalPages(),
                pageNo,
                flightScheduleResponses.size(),
                flightScheduleResponses,
                flightSchedulePage.hasNext(),
                flightSchedulePage.hasPrevious()
        );
    }
}
