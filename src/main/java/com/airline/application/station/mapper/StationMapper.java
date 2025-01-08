package com.railgo.application.station.mapper;

import com.railgo.application.station.dataTransferObject.request.AddStationRequest;
import com.railgo.application.station.dataTransferObject.response.StationResponse;
import com.railgo.domain.station.model.Station;
import com.railgo.infrastructure.config.GlobalMapperConfig;
import com.railgo.infrastructure.mapper.MapperEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface StationMapper extends MapperEntity<Station, StationResponse> {
    Station toStation(AddStationRequest request);
}
