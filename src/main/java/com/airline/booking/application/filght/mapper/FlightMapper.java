package com.airline.booking.application.filght.mapper;

import com.airline.booking.application.filght.dataTransferObject.request.AddFlightRequest;
import com.airline.booking.application.filght.dataTransferObject.response.FlightResponse;
import com.airline.booking.domain.flight.model.Flight;
import com.airline.booking.infrastructure.config.GlobalMapperConfig;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface FlightMapper {
    Flight toFlight(AddFlightRequest request);
    FlightResponse toResponse(Flight flights);
    List<FlightResponse> toResponses(List<Flight> flights);
}
