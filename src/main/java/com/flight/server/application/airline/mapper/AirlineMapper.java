package com.flight.server.application.airline.mapper;

import com.flight.server.application.airline.dataTransferObject.request.AddAirlineRequest;
import com.flight.server.application.airline.dataTransferObject.response.AirlineResponse;
import com.flight.server.domain.airline.model.Airline;
import com.flight.server.infrastructure.config.GlobalMapperConfig;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface AirlineMapper {
    Airline toAirline(AddAirlineRequest request);
    AirlineResponse toResponse(Airline airline);
}
