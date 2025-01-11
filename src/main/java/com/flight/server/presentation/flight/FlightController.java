package com.flight.server.presentation.flight;

import com.flight.server.application.filght.dataTransferObject.request.AddFlightRequest;
import com.flight.server.application.filght.dataTransferObject.response.FlightReservation;
import com.flight.server.application.filght.dataTransferObject.response.FlightResponse;
import com.flight.server.application.filght.service.IFlightUseCase;
import com.flight.server.application.utils.PageResponse;
import com.flight.server.infrastructure.exception.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/${api.prefix}/flight")
@Tag(name = "Flight Controller")
public class FlightController {
    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    private final IFlightUseCase flightUseCase;

    public FlightController(IFlightUseCase flightUseCase) {
        this.flightUseCase = flightUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<FlightResponse>> handelAddFlight(
            @RequestBody AddFlightRequest request
    ) {
        logger.info("Adding flight: {}", request);
        var result = flightUseCase.addFlight(request);
        return ApiResponse.<FlightResponse>build()
                .withData(result)
                .toEntity();
    }

    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<FlightReservation>> handleGetFlight(
            @RequestParam String flightId,
            @RequestParam(defaultValue = "0") int childSeats,
            @RequestParam(defaultValue = "1") int adultSeats,
            HttpSession session
    ) {
        String sessionId = session.getId();
        logger.info("Getting flight with id: {}, childSeats: {}, adultSeats: {}, sessionId: {}",
                flightId, childSeats, adultSeats, sessionId);
        var result = flightUseCase.getFlight(
                flightId,
                childSeats, adultSeats,
                sessionId
        );
        return ApiResponse.<FlightReservation>build()
                .withData(result)
                .toEntity();
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<FlightReservation>>> handelSearchFlight(
//            @RequestParam(defaultValue = "one-way") String tripType,
//            @RequestParam(required = false) LocalDateTime returnTime,
            @RequestParam(required = false) String departureAirportId,
            @RequestParam(required = false) String arrivalAirportId,
            @RequestParam(required = false) LocalDate departureTime,
            @RequestParam(defaultValue = "0") int childSeats,
            @RequestParam(defaultValue = "1") int adultSeats,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "price") String sortBy
    ) {
        logger.info("Searching for flights with parameters: " +
                        "departureAirportId={}, arrivalAirportId={} , " +
                        "departureTime={}, " +
                        "childSeats={}, adultSeats={}," +
                        "page={}, pageSize={}, sortBy={}",
                departureAirportId, arrivalAirportId,
                departureTime,
                childSeats, adultSeats,
                page, pageSize, sortBy
        );

        var result = flightUseCase.searchFlight(
                departureAirportId, arrivalAirportId,
                departureTime,
                childSeats, adultSeats,
                page, pageSize, sortBy
        );
        return ApiResponse.<PageResponse<FlightReservation>>build()
                .withData(result)
                .toEntity();
    }
}
