package com.airline.booking.application.filght.service;


import com.airline.booking.application.filght.dataTransferObject.request.AddFlightRequest;
import com.airline.booking.application.filght.dataTransferObject.response.FlightReservation;
import com.airline.booking.application.filght.dataTransferObject.response.FlightResponse;
import com.airline.booking.application.utils.PageResponse;

import java.time.LocalDate;

public interface IFlightUseCase {
    FlightResponse addFlight(AddFlightRequest request);

    FlightReservation getFlight(
            String flightId,
            int childSeats, int adultSeats,
            String sessionId
    );

    /**
     * Searches for flights based on the provided criteria.
     *
     * @param departureAirportCode the Code of the departure airport
     * @param arrivalAirportCode   the Code of the arrival airport
     * @param departureTime      the desired departure time for the flight
     * @param childSeats         the number of child seats required
     * @param adultSeats         the number of adult seats required
     * @param page               the page number for pagination (starting from 0)
     * @param pageSize           the number of items per page
     * @param sortBy             the field to sort the results by (e.g., "price", "duration")
     * @return a paginated list of flights matching the specified criteria,
     * encapsulated in a {@link PageResponse} containing {@link FlightReservation} objects
     */
    PageResponse<FlightReservation> searchFlight(
            String departureAirportCode, String arrivalAirportCode,
            LocalDate departureTime,
            int childSeats, int adultSeats,
            int page, int pageSize,
             String sortBy
    );
}
