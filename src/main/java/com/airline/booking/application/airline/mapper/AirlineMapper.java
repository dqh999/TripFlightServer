package com.airline.booking.application.airline.mapper;

import com.airline.booking.application.airline.dataTransferObject.request.AddAirlineRequest;
import com.airline.booking.application.airline.dataTransferObject.response.AirlineResponse;
import com.airline.booking.domain.airline.model.Airline;
import com.airline.booking.infrastructure.config.GlobalMapperConfig;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface AirlineMapper {
    Airline toAirline(AddAirlineRequest request);
    AirlineResponse toResponse(Airline airline);
}
