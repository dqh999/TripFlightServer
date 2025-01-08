package com.airline.presentation.flight;

import com.airline.application.airplane.dataTransferObject.request.AddFlightRequest;
import com.airline.application.airplane.dataTransferObject.request.AddFlightScheduleRequest;
import com.airline.application.airplane.service.IFlightUseCase;
import com.airline.application.airplane.service.IAirplaneUseCase;
import com.airline.infrastructure.exception.ApiResponse;
import com.airline.infrastructure.security.UserDetail;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/${api.prefix}/flight")
@Tag(name = "Flight Controller")
public class FlightController {
    private final IAirplaneUseCase flightUseCase;
    private final IFlightUseCase flightScheduleUseCase;

    @Autowired
    public FlightController(IAirplaneUseCase flightUseCase,
                            IFlightUseCase flightScheduleUseCase) {
        this.flightUseCase = flightUseCase;
        this.flightScheduleUseCase = flightScheduleUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> handelAddFlight(@AuthenticationPrincipal UserDetail userRequest,
                                            @RequestBody AddFlightRequest request) {
        var result = flightUseCase.addFlight(userRequest, request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }

    @PostMapping("/schedule")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> handelAddSchedule(@AuthenticationPrincipal UserDetail userRequest,
                                               @RequestBody AddFlightScheduleRequest request) {
        var result = flightScheduleUseCase.addSchedule(userRequest, request);
        return ApiResponse.build().withData(result).toEntity();
    }

    @GetMapping("/schedule")
    public ResponseEntity<?> handelGetAllSchedules(
            @RequestParam(defaultValue = "one-way") String tripType,
            @RequestParam(required = false) LocalDateTime returnTime,
            @RequestParam String departureAirportId,
            @RequestParam String arrivalAirportId,
            @RequestParam LocalDateTime departureTime,
            @RequestParam(defaultValue = "0") int childSeats,
            @RequestParam(defaultValue = "1") int adultSeats,
            @RequestParam(defaultValue = "0") int seniorSeats,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "s") String sortBy) {
        var result = flightScheduleUseCase.getAllSchedules(
                departureAirportId, arrivalAirportId,
                departureTime,
                childSeats, adultSeats, seniorSeats,
                page, pageSize, sortBy);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
}
