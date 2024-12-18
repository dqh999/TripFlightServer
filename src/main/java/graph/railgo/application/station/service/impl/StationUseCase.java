package graph.railgo.application.station.service.impl;

import graph.railgo.application.station.dataTransferObject.request.AddStationRequest;
import graph.railgo.application.station.dataTransferObject.response.StationResponse;
import graph.railgo.application.station.mapper.StationMapper;
import graph.railgo.application.station.service.IStationUseCase;
import graph.railgo.application.utils.PermissionUtils;
import graph.railgo.domain.station.model.Station;
import graph.railgo.domain.station.service.IStationService;
import graph.railgo.infrastructure.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationUseCase implements IStationUseCase {
    private final IStationService stationService;
    private final StationMapper stationMapper;

    @Autowired
    public StationUseCase(IStationService stationService, StationMapper stationMapper) {
        this.stationService = stationService;
        this.stationMapper = stationMapper;
    }

    @Override
    public StationResponse addStation(UserDetail userRequest,
                                      AddStationRequest request) {
        PermissionUtils.hasPermission(userRequest, UserDetail.roleAdmin);

        Station stationObject = stationMapper.toStation(request);
        Station newStation = stationService.addStation(stationObject);

        return stationMapper.toDTO(newStation);
    }
}
