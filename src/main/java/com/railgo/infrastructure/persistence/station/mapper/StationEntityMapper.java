package com.railgo.infrastructure.persistence.station.mapper;

import com.railgo.domain.station.model.Station;
import com.railgo.infrastructure.config.GlobalMapperConfig;
import com.railgo.infrastructure.mapper.MapperEntity;
import com.railgo.infrastructure.persistence.station.model.StationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface StationEntityMapper extends MapperEntity<StationEntity, Station> {
}
