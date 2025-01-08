package com.railgo.application.station.service;

import com.railgo.application.station.dataTransferObject.request.AddStationRequest;
import com.railgo.application.station.dataTransferObject.request.AddStationRouteRequest;
import com.railgo.application.station.dataTransferObject.response.StationResponse;
import com.railgo.application.station.dataTransferObject.response.StationRouteResponse;
import com.railgo.application.utils.PageResponse;
import com.railgo.infrastructure.security.UserDetail;

public interface IStationUseCase {
    StationResponse addStation(UserDetail userRequest,
                               AddStationRequest request);
    StationResponse getStation(String id);
    PageResponse<StationResponse> searchStation(String keyword, int pageNo, int pageSize);
    StationRouteResponse addRoute(UserDetail userRequest,
                                  AddStationRouteRequest request);
}
