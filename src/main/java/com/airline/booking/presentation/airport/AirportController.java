package com.airline.booking.presentation.airport;

import com.airline.booking.application.airport.dataTransferObject.request.AddAirportRequest;
import com.airline.booking.application.airport.dataTransferObject.response.AirportResponse;
import com.airline.booking.application.airport.service.IAirportUseCase;
import com.airline.booking.application.utils.PageResponse;
import com.airline.booking.infrastructure.exception.ApiResponse;
import com.airline.booking.infrastructure.security.UserDetail;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.prefix}/airport")
@Tag(name = "Airport Controller")
public class AirportController {
    private static final Logger logger = LoggerFactory.getLogger(AirportController.class);

    private final IAirportUseCase airportUseCase;

    @Autowired
    public AirportController(IAirportUseCase airportUseCase) {
        this.airportUseCase = airportUseCase;
    }


    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<AirportResponse>> handleAddAirport(
            @AuthenticationPrincipal UserDetail userRequest,
                                                                         @RequestBody AddAirportRequest request
    ) {
        var result = airportUseCase.add(userRequest, request);
        return ApiResponse.<AirportResponse>build()
                .withData(result)
                .toEntity();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AirportResponse>> getAirport(
            @PathVariable("id") String id
    ) {
        var result = airportUseCase.getById(id);
        return ApiResponse.<AirportResponse>build()
                .withData(result)
                .toEntity();
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<AirportResponse>>> handleSearchAirport(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize
    ){
        var result = airportUseCase.search(keyword,pageNo,pageSize);
        return ApiResponse.<PageResponse<AirportResponse>>build()
                .withData(result)
                .toEntity();
    }
}
