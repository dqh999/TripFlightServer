package com.flight.server.application.filght.mapper;

import com.flight.server.application.filght.dataTransferObject.request.AddFlightRequest;
import com.flight.server.application.filght.dataTransferObject.response.FlightResponse;
import com.flight.server.domain.flight.model.Flight;
import com.flight.server.infrastructure.config.GlobalMapperConfig;
import com.flight.server.infrastructure.mapper.MapperEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface FlightMapper {
    Flight toFlight(AddFlightRequest request);
    FlightResponse toResponse(Flight flights);
    List<FlightResponse> toResponses(List<Flight> flights);
}
