package com.airline.presentation.airport;

import com.airline.application.airport.dataTransferObject.request.AddAirportRequest;
import com.airline.application.airport.service.IAirportUseCase;
import com.airline.infrastructure.exception.ApiResponse;
import com.airline.infrastructure.security.UserDetail;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/${api.prefix}/airport")
@Tag(name = "airline Controller")
public class AirportController {
    private final IAirportUseCase airportUseCase;

    @Autowired
    public AirportController(IAirportUseCase airportUseCase) {
        this.airportUseCase = airportUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> handleAddAirport(@AuthenticationPrincipal UserDetail userRequest,
                                              @RequestBody AddAirportRequest request) {
        var result = airportUseCase.add(userRequest, request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAirport(@PathVariable("id") String id) {
        var result = airportUseCase.getById(id);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
    @GetMapping("/search")
    public ResponseEntity<?> handleSearchAirport(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize
    ){
        var result = airportUseCase.search(keyword,pageNo,pageSize);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
}
