package com.airline.application.airplane.mapper;

import com.airline.application.airplane.dataTransferObject.request.AddFlightRequest;
import com.airline.application.airplane.dataTransferObject.response.FlightResponse;
import com.airline.domain.airplane.model.Airplane;
import com.airline.infrastructure.config.GlobalMapperConfig;
import com.airline.infrastructure.mapper.MapperEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface FlightMapper extends MapperEntity<Airplane, FlightResponse> {
    Airplane toFlight(AddFlightRequest request);
}
