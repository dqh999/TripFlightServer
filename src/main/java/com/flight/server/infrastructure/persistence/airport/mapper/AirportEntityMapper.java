package com.flight.server.infrastructure.persistence.airport.mapper;

import com.flight.server.domain.airport.model.Airport;
import com.flight.server.infrastructure.config.GlobalMapperConfig;
import com.flight.server.infrastructure.mapper.MapperEntity;
import com.flight.server.infrastructure.persistence.airport.model.AirportEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface AirportEntityMapper extends MapperEntity<AirportEntity, Airport> {
}
