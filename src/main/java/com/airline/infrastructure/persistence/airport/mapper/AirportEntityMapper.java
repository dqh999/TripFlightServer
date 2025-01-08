package com.airline.infrastructure.persistence.airport.mapper;

import com.airline.domain.airline.model.airline;
import com.airline.infrastructure.config.GlobalMapperConfig;
import com.airline.infrastructure.mapper.MapperEntity;
import com.airline.infrastructure.persistence.airport.model.AirportEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface AirportEntityMapper extends MapperEntity<AirportEntity, airline> {
}
