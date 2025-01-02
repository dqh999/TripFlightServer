package com.railgo.application.station.mapper;

import com.railgo.application.station.dataTransferObject.request.AddStationRouteRequest;
import com.railgo.application.station.dataTransferObject.response.StationRouteResponse;
import com.railgo.domain.station.model.StationRoute;
import com.railgo.infrastructure.config.GlobalMapperConfig;
import com.railgo.infrastructure.mapper.MapperEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface StationRouteMapper extends MapperEntity<StationRoute, StationRouteResponse> {

    StationRoute toEntity(AddStationRouteRequest request);

    @Override
    StationRouteResponse toDTO(StationRoute route);

    @Override
    List<StationRoute> toEntities(List<StationRouteResponse> stationRouteResponses);

    @Override
    List<StationRouteResponse> toDTOs(List<StationRoute> routes);
}
