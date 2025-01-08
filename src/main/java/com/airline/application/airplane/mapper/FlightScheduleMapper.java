package com.airline.application.airplane.mapper;

import com.airline.application.airplane.dataTransferObject.response.FlightScheduleResponse;
import com.airline.domain.airplane.model.Flight;
import com.airline.infrastructure.config.GlobalMapperConfig;
import com.airline.infrastructure.mapper.MapperEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface FlightScheduleMapper extends MapperEntity<Flight, FlightScheduleResponse> {
}
