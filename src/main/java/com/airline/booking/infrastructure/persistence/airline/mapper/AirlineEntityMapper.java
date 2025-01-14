package com.airline.booking.infrastructure.persistence.airline.mapper;

import com.airline.booking.domain.airline.model.Airline;
import com.airline.booking.infrastructure.config.GlobalMapperConfig;
import com.airline.booking.infrastructure.mapper.MapperEntity;
import com.airline.booking.infrastructure.persistence.airline.model.AirlineEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface AirlineEntityMapper extends MapperEntity<AirlineEntity, Airline> {
}
