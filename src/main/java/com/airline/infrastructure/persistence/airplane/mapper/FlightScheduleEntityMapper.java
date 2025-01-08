package com.airline.infrastructure.persistence.airplane.mapper;

import com.airline.domain.airplane.model.Flight;
import com.airline.infrastructure.config.GlobalMapperConfig;
import com.airline.infrastructure.mapper.MapperEntity;
import com.airline.infrastructure.persistence.airplane.model.FlightEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface FlightScheduleEntityMapper extends MapperEntity<FlightEntity, Flight> {
}
