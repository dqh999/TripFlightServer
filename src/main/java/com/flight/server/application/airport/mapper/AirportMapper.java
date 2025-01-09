package com.flight.server.application.airport.mapper;

import com.flight.server.application.airport.dataTransferObject.request.AddAirportRequest;
import com.flight.server.application.airport.dataTransferObject.response.AirportResponse;
import com.flight.server.domain.airport.model.Airport;
import com.flight.server.infrastructure.config.GlobalMapperConfig;
import com.flight.server.infrastructure.mapper.MapperEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface AirportMapper extends MapperEntity<Airport, AirportResponse> {
    Airport toAirport(AddAirportRequest request);
}
