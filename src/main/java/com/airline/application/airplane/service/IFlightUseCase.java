package com.airline.application.airplane.service;

import com.airline.application.airplane.dataTransferObject.request.AddFlightScheduleRequest;
import com.airline.application.airplane.dataTransferObject.response.FlightScheduleResponse;
import com.airline.application.utils.PageResponse;
import com.airline.infrastructure.security.UserDetail;

import java.time.LocalDateTime;

public interface IFlightUseCase {
    FlightScheduleResponse addSchedule(UserDetail userRequest, AddFlightScheduleRequest request);

    PageResponse<FlightScheduleResponse> getAllSchedules(String departureAirportId, String arrivalAirportId,
                                                        LocalDateTime departureTime,
                                                        int childSeats, int adultSeats, int seniorSeats,
                                                        int pageNo, int pageSize, String sortBy);

}
