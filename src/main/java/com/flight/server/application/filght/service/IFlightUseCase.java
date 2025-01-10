package com.flight.server.application.filght.service;


import com.flight.server.application.filght.dataTransferObject.request.AddFlightRequest;
import com.flight.server.application.filght.dataTransferObject.response.FlightResponse;
import com.flight.server.application.utils.PageResponse;

import java.time.LocalDate;

public interface IFlightUseCase {
    FlightResponse addFlight(AddFlightRequest request);

    FlightResponse getFlight(
            String fightId,
            int childSeats, int adultSeats,
            String sessionId
    );

    /**
     * Searches for flights based on the provided criteria.
     *
     * @param departureAirportId the ID of the departure airport
     * @param arrivalAirportId   the ID of the arrival airport
     * @param departureTime      the desired departure time for the flight
     * @param childSeats         the number of child seats required
     * @param adultSeats         the number of adult seats required
     * @param page               the page number for pagination (starting from 0)
     * @param pageSize           the number of items per page
     * @param sortBy             the field to sort the results by (e.g., "price", "duration")
     * @return a paginated list of flights matching the specified criteria,
     * encapsulated in a {@link PageResponse} containing {@link FlightResponse} objects
     */
    PageResponse<FlightResponse> searchFlight(
            String departureAirportId, String arrivalAirportId,
            LocalDate departureTime,
            int childSeats, int adultSeats,
            int page, int pageSize,
            String sortBy
    );
}
