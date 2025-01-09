package com.flight.server.presentation.flight;

import com.flight.server.application.filght.dataTransferObject.request.AddFlightRequest;
import com.flight.server.application.filght.service.IFlightUseCase;
import com.flight.server.infrastructure.exception.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/${api.prefix}/flight")
@Tag(name = "Flight Controller")
public class FlightController {
    private final IFlightUseCase flightUseCase;
    public FlightController(IFlightUseCase flightUseCase) {
        this.flightUseCase = flightUseCase;
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> handelAddFlight(@RequestBody AddFlightRequest request) {
        var result = flightUseCase.addFlight(request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }


    @GetMapping("/schedule")
    public ResponseEntity<?> handelGetAllSchedules(
//            @RequestParam(defaultValue = "one-way") String tripType,
//            @RequestParam(required = false) LocalDateTime returnTime,
            @RequestParam String departureAirportId,
            @RequestParam String arrivalAirportId,
            @RequestParam LocalDateTime departureTime,
            @RequestParam(defaultValue = "0") int childSeats,
            @RequestParam(defaultValue = "1") int adultSeats,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "s") String sortBy) {
        var result = flightUseCase.search(
                departureAirportId, arrivalAirportId,
                departureTime,
                childSeats, adultSeats,
                page, pageSize, sortBy);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
}
