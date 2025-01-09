package com.flight.server.application.filght.service.impl;


import com.flight.server.application.filght.dataTransferObject.request.AddFlightRequest;
import com.flight.server.application.filght.dataTransferObject.response.FlightResponse;
import com.flight.server.application.filght.mapper.FlightMapper;
import com.flight.server.application.filght.service.IFlightUseCase;
import com.flight.server.application.utils.PageResponse;
import com.flight.server.domain.flight.model.Flight;
import com.flight.server.domain.flight.service.IFlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class FlightUseCase implements IFlightUseCase {
    private static final Logger logger = LoggerFactory.getLogger(FlightUseCase.class);
    private final IFlightService flightService;
    private final FlightMapper flightMapper;

    public FlightUseCase(
            IFlightService flightService,
            FlightMapper flightMapper
    ) {
        this.flightService = flightService;
        this.flightMapper = flightMapper;
    }

    @Override
    public FlightResponse addFlight(AddFlightRequest request) {
        Flight flight = flightMapper.toFlight(request);
        Flight newFlight = flightService.create(flight);
        return flightMapper.toDTO(newFlight);
    }

    @Override
    public PageResponse<FlightResponse> search(
            String departureAirportId, String arrivalAirportId,
            LocalDateTime departureTime,
            int childSeats, int adultSeats,
            int page, int pageSize,
            String sortBy
    ) {
        Page<Flight> flightPage = flightService.getFlights(
                departureAirportId, arrivalAirportId,
                departureTime,
                page, pageSize
        );
        List<FlightResponse> flightRes = flightMapper.toDTOs(flightPage.getContent());
        return new PageResponse<>(
                (int) flightPage.getTotalElements(),
                flightPage.getTotalPages(),
                page,
                flightPage.getSize(),
                flightRes,
                flightPage.hasNext(),
                flightPage.hasPrevious()
        );
    }
}
