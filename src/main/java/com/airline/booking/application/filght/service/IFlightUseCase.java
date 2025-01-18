package com.airline.booking.application.filght.service;


import com.airline.booking.application.filght.dataTransferObject.request.AddFlightRequest;
import com.airline.booking.application.filght.dataTransferObject.request.SearchFlightRequest;
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
    PageResponse<FlightReservation> searchFlight(SearchFlightRequest request);
}
