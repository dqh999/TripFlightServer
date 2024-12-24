package com.railgo.application.station.service;

import com.railgo.application.station.dataTransferObject.request.AddStationRequest;
import com.railgo.application.station.dataTransferObject.request.AddStationRouteRequest;
import com.railgo.application.station.dataTransferObject.response.StationResponse;
import com.railgo.application.station.dataTransferObject.response.StationRouteResponse;
import com.railgo.infrastructure.security.UserDetail;

public interface IStationUseCase {
    StationResponse addStation(UserDetail userRequest,
                               AddStationRequest request);
    StationResponse getStation(String id);
    StationRouteResponse addRoute(UserDetail userRequest,
                                  AddStationRouteRequest request);
}
