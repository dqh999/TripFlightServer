package com.airline.application.airport.mapper;

import com.airline.application.airport.dataTransferObject.request.AddAirportRequest;
import com.airline.application.airport.dataTransferObject.response.AirportResponse;
import com.airline.domain.airport.model.Airport;
import com.airline.infrastructure.config.GlobalMapperConfig;
import com.airline.infrastructure.mapper.MapperEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface AirportMapper extends MapperEntity<Airport, AirportResponse> {
    Airport toAirport(AddAirportRequest request);
}
