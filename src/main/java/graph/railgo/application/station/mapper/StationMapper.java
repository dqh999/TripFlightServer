package graph.railgo.application.station.mapper;

import graph.railgo.application.station.dataTransferObject.request.AddStationRequest;
import graph.railgo.application.station.dataTransferObject.response.StationResponse;
import graph.railgo.domain.station.model.Station;
import graph.railgo.infrastructure.config.GlobalMapperConfig;
import graph.railgo.infrastructure.mapper.MapperEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface StationMapper extends MapperEntity<Station, StationResponse> {
    Station toStation(AddStationRequest request);
}
