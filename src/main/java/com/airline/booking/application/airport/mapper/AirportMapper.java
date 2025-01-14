package com.airline.booking.application.airport.mapper;

import com.airline.booking.application.airport.dataTransferObject.request.AddAirportRequest;
import com.airline.booking.application.airport.dataTransferObject.response.AirportResponse;
import com.airline.booking.domain.airport.model.Airport;
import com.airline.booking.infrastructure.config.GlobalMapperConfig;
import com.airline.booking.infrastructure.mapper.MapperEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface AirportMapper extends MapperEntity<Airport, AirportResponse> {
    Airport toAirport(AddAirportRequest request);
}
