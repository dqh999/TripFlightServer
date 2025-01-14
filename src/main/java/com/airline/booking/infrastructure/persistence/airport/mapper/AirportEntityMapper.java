package com.airline.booking.infrastructure.persistence.airport.mapper;

import com.airline.booking.domain.airport.model.Airport;
import com.airline.booking.infrastructure.config.GlobalMapperConfig;
import com.airline.booking.infrastructure.mapper.MapperEntity;
import com.airline.booking.infrastructure.persistence.airport.model.AirportEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface AirportEntityMapper extends MapperEntity<AirportEntity, Airport> {
}
