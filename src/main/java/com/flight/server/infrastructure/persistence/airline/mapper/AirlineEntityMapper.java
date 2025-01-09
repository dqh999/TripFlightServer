package com.flight.server.infrastructure.persistence.airline.mapper;

import com.flight.server.domain.airline.model.Airline;
import com.flight.server.infrastructure.config.GlobalMapperConfig;
import com.flight.server.infrastructure.mapper.MapperEntity;
import com.flight.server.infrastructure.persistence.airline.model.AirlineEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface AirlineEntityMapper extends MapperEntity<AirlineEntity, Airline> {
}
