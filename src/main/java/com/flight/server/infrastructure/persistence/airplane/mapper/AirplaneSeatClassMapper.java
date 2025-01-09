package com.flight.server.infrastructure.persistence.airplane.mapper;

import com.flight.server.domain.airplane.model.AirplaneSeatClass;
import com.flight.server.infrastructure.config.GlobalMapperConfig;
import com.flight.server.infrastructure.mapper.MapperEntity;
import com.flight.server.infrastructure.persistence.airplane.model.AirplaneSeatClassEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface AirplaneSeatClassMapper extends MapperEntity<AirplaneSeatClassEntity, AirplaneSeatClass> {
}
