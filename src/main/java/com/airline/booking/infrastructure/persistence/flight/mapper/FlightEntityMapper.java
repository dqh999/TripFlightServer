package com.airline.booking.infrastructure.persistence.flight.mapper;

import com.airline.booking.domain.flight.model.Flight;
import com.airline.booking.infrastructure.config.GlobalMapperConfig;
import com.airline.booking.infrastructure.mapper.MapperEntity;
import com.airline.booking.infrastructure.persistence.flight.model.FlightEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface FlightEntityMapper extends MapperEntity<FlightEntity, Flight> {
}
