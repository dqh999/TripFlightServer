package com.flight.server.infrastructure.persistence.flight.mapper;

import com.flight.server.domain.flight.model.Flight;
import com.flight.server.infrastructure.config.GlobalMapperConfig;
import com.flight.server.infrastructure.mapper.MapperEntity;
import com.flight.server.infrastructure.persistence.flight.model.FlightEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface FlightEntityMapper extends MapperEntity<FlightEntity, Flight> {
}
