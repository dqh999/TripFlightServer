package com.airline.booking.presentation.airline;

import com.airline.booking.application.airline.dataTransferObject.request.AddAirlineRequest;
import com.airline.booking.application.airline.dataTransferObject.response.AirlineResponse;
import com.airline.booking.application.airline.service.IAirlineUseCase;
import com.airline.booking.infrastructure.exception.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${api.prefix}/airline")
@Tag(name = "Airline Controller")
public class AirlineController {
    private static final Logger logger = LoggerFactory.getLogger(AirlineController.class);

    private final IAirlineUseCase airlineUseCase;

    public AirlineController(IAirlineUseCase airlineUseCase) {
        this.airlineUseCase = airlineUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<AirlineResponse>> handleAddAirline(@RequestBody AddAirlineRequest request) {
        var result = airlineUseCase.add(request);
        return ApiResponse.<AirlineResponse>build()
                .withData(result)
                .toEntity();
    }
}
