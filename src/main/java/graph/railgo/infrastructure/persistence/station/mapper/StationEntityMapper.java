package graph.railgo.infrastructure.persistence.station.mapper;

import graph.railgo.domain.station.model.Station;
import graph.railgo.infrastructure.config.GlobalMapperConfig;
import graph.railgo.infrastructure.mapper.MapperEntity;
import graph.railgo.infrastructure.persistence.station.model.StationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface StationEntityMapper extends MapperEntity<StationEntity, Station> {
}
