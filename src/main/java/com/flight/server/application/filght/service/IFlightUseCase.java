package com.flight.server.application.filght.service;


import com.flight.server.application.filght.dataTransferObject.request.AddFlightRequest;
import com.flight.server.application.filght.dataTransferObject.response.FlightResponse;
import com.flight.server.application.utils.PageResponse;

import java.time.LocalDateTime;

public interface IFlightUseCase {
    FlightResponse addFlight(AddFlightRequest request);

    PageResponse<FlightResponse> search(
            String departureAirportId, String arrivalAirportId,
            LocalDateTime departureTime,
            int childSeats, int adultSeats,
            int page, int pageSize,
            String sortBy
    );
}
