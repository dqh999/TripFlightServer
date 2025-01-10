package com.flight.server.application.airport.service;

import com.flight.server.application.airport.dataTransferObject.request.AddAirportRequest;
import com.flight.server.application.airport.dataTransferObject.response.AirportResponse;
import com.flight.server.application.utils.PageResponse;
import com.flight.server.infrastructure.security.UserDetail;

public interface IAirportUseCase {
    AirportResponse add(UserDetail userRequest,
                               AddAirportRequest request);
    AirportResponse getById(String id);
    PageResponse<AirportResponse> search(String keyword, int pageNo, int pageSize);
}
