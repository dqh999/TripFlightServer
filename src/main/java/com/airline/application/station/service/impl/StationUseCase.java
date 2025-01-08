package com.railgo.application.station.service.impl;

import com.railgo.application.station.dataTransferObject.request.AddStationRequest;
import com.railgo.application.station.dataTransferObject.request.AddStationRouteRequest;
import com.railgo.application.station.dataTransferObject.response.StationResponse;
import com.railgo.application.station.dataTransferObject.response.StationRouteResponse;
import com.railgo.application.station.mapper.StationMapper;
import com.railgo.application.station.mapper.StationRouteMapper;
import com.railgo.application.station.service.IStationUseCase;
import com.railgo.application.utils.PageResponse;
import com.railgo.domain.station.model.Station;
import com.railgo.domain.station.model.StationRoute;
import com.railgo.domain.station.service.IStationRouteService;
import com.railgo.domain.station.service.IStationService;
import com.railgo.infrastructure.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationUseCase implements IStationUseCase {
    private final IStationService stationService;
    private final IStationRouteService stationRouteService;
    private final StationMapper stationMapper;
    private final StationRouteMapper stationRouteMapper;

    @Autowired
    public StationUseCase(IStationService stationService,
                          IStationRouteService stationRouteService,
                          StationMapper stationMapper,
                          StationRouteMapper stationRouteMapper) {
        this.stationService = stationService;
        this.stationRouteService = stationRouteService;
        this.stationMapper = stationMapper;
        this.stationRouteMapper = stationRouteMapper;
    }

    @Override
    public StationResponse addStation(UserDetail userRequest,
                                      AddStationRequest request) {

        Station stationObject = stationMapper.toStation(request);
        Station newStation = stationService.addStation(stationObject);

        return stationMapper.toDTO(newStation);
    }

    @Override
    public StationResponse getStation(String id) {
        Station existingStation = stationService.getStation(id);
        return stationMapper.toDTO(existingStation);
    }

    @Override
    public PageResponse<StationResponse>  searchStation(String keyword, int pageNo, int pageSize) {
        Page<Station> pageResult = stationService.search(keyword,pageNo-1,pageSize);
        List<StationResponse> stationResponses = pageResult.getContent().stream().map(stationMapper::toDTO).collect(Collectors.toList());

        return new PageResponse<>(
                (int) pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageNo,
                pageSize,
                stationResponses,
                pageResult.hasNext(),
                pageResult.hasPrevious()
        );
    }

    @Override
    public StationRouteResponse addRoute(UserDetail userRequest, AddStationRouteRequest request) {

        StationRoute newStationRoute = stationRouteMapper.toEntity(request);
        Station stationA = stationService.getStation(request.getStationAId());
        Station stationB = stationService.getStation(request.getStationBId());
        newStationRoute.setStationA(stationA);
        newStationRoute.setStationB(stationB);

        stationRouteService.addRoute(newStationRoute);

        return stationRouteMapper.toDTO(newStationRoute);
    }
}
