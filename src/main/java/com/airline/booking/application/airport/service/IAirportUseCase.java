package com.airline.booking.application.airport.service;

import com.airline.booking.application.airport.dataTransferObject.request.AddAirportRequest;
import com.airline.booking.application.airport.dataTransferObject.response.AirportResponse;
import com.airline.booking.application.utils.PageResponse;
import com.airline.booking.infrastructure.security.UserDetail;

public interface IAirportUseCase {
    AirportResponse add(UserDetail userRequest,
                               AddAirportRequest request);
    AirportResponse getById(String id);
    PageResponse<AirportResponse> search(String keyword, int pageNo, int pageSize);
}
