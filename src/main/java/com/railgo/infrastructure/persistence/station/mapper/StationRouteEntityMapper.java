package com.railgo.infrastructure.persistence.station.mapper;

import com.railgo.domain.station.model.StationRoute;
import com.railgo.infrastructure.config.GlobalMapperConfig;
import com.railgo.infrastructure.mapper.MapperEntity;
import com.railgo.infrastructure.persistence.station.model.StationRouteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface StationRouteEntityMapper extends MapperEntity<StationRouteEntity, StationRoute> {
}
