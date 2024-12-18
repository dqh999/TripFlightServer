package graph.railgo.infrastructure.persistence.station.mapper;

import graph.railgo.domain.station.model.StationRoute;
import graph.railgo.infrastructure.config.GlobalMapperConfig;
import graph.railgo.infrastructure.mapper.MapperEntity;
import graph.railgo.infrastructure.persistence.station.model.StationRouteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface StationRouteEntityMapper extends MapperEntity<StationRouteEntity, StationRoute> {
}
