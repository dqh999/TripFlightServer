package com.airline.application.airport.service;

import com.airline.application.airport.dataTransferObject.request.AddAirportRequest;
import com.airline.application.airport.dataTransferObject.response.AirportResponse;
import com.airline.application.utils.PageResponse;
import com.airline.infrastructure.security.UserDetail;

public interface IAirportUseCase {
    AirportResponse add(UserDetail userRequest,
                               AddAirportRequest request);
    AirportResponse getById(String id);
    PageResponse<AirportResponse> search(String keyword, int pageNo, int pageSize);
}
