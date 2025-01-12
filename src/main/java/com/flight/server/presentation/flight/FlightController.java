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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${api.prefix}/flight")
@Tag(name = "Flight Controller")
public class FlightController {
    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    private final IFlightUseCase flightUseCase;

    public FlightController(IFlightUseCase flightUseCase) {
        this.flightUseCase = flightUseCase;
    }

    @PostMapping("/list")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<FlightResponse>>> handelAddFlights(
            @RequestBody List<AddFlightRequest> requests
    ) {
        logger.info("Adding flights: {}", requests);
        List<FlightResponse> results = requests.stream().map(flightUseCase::addFlight).collect(Collectors.toList());
        return ApiResponse.<List<FlightResponse>>build()
                .withData(results)
                .toEntity();
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
            @RequestParam(required = false) String departureAirportCode,
            @RequestParam(required = false) String arrivalAirportCode,
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
                departureAirportCode, arrivalAirportCode,
                departureTime,
                childSeats, adultSeats,
                page, pageSize, sortBy
        );

        var result = flightUseCase.searchFlight(
                departureAirportCode, arrivalAirportCode,
                departureTime,
                childSeats, adultSeats,
                page, pageSize, sortBy
        );
        return ApiResponse.<PageResponse<FlightReservation>>build()
                .withData(result)
                .toEntity();
    }
}
