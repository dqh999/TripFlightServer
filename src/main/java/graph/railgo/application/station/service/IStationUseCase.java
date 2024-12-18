package graph.railgo.application.station.service;

import graph.railgo.application.station.dataTransferObject.request.AddStationRequest;
import graph.railgo.application.station.dataTransferObject.response.StationResponse;
import graph.railgo.infrastructure.security.UserDetail;

public interface IStationUseCase {
    StationResponse addStation(UserDetail userRequest,
                               AddStationRequest request);
}
