package com.flight.server.application.filght.service.impl;


import com.flight.server.application.filght.dataTransferObject.request.AddFlightRequest;
import com.flight.server.application.filght.dataTransferObject.response.FlightResponse;
import com.flight.server.application.filght.mapper.FlightMapper;
import com.flight.server.application.filght.service.IFlightUseCase;
import com.flight.server.application.utils.PageResponse;
import com.flight.server.domain.flight.model.Flight;
import com.flight.server.domain.flight.service.IFlightPriceService;
import com.flight.server.domain.flight.service.IFlightService;
import com.flight.server.domain.utils.valueObject.Money;
import com.flight.server.infrastructure.service.cache.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class FlightUseCase implements IFlightUseCase {
    private static final Logger logger = LoggerFactory.getLogger(FlightUseCase.class);

    private final IFlightService flightService;
    private final IFlightPriceService flightPriceService;
    private final FlightMapper flightMapper;
    private final CacheService cacheService;

    @Value("${spring.data.redis.key.flight.detail}")
    private String flightDetailKey;

    public FlightUseCase(
            IFlightService flightService,
            IFlightPriceService flightPriceService,
            FlightMapper flightMapper,
            CacheService cacheService
    ) {
        this.flightService = flightService;
        this.flightPriceService = flightPriceService;
        this.flightMapper = flightMapper;
        this.cacheService = cacheService;
    }

    @Override
    public FlightResponse addFlight(AddFlightRequest request) {
        Flight flight = flightMapper.toFlight(request);
        Flight newFlight = flightService.create(flight);
        logger.info("Successfully added new flight with ID: {}", newFlight.getId());

        return buildResponse(newFlight);
    }
    private FlightResponse buildResponse(
            Flight flight
    ) {
        return flightMapper.toResponse(flight);
    }
    @Override
    public FlightResponse getFlight(
            String fightId,
            int childSeats, int adultSeats,
            String sessionId
    ) {
        String cacheKey = flightDetailKey.formatted(
                fightId,
                sessionId
        );

        FlightResponse existFlightRes;
        if (!cacheService.exists(cacheKey)) {
            Flight existFlight = flightService.getById(fightId);

            Money pricePare = flightPriceService.calculateTotalFare(
                    null,
                    childSeats, adultSeats
            );

            cacheService.put(
                    cacheKey,
                    existFlight,
                    60000
            );
        } else {
            existFlightRes = cacheService.get(cacheKey, FlightResponse.class);
        }

        FlightResponse response = new FlightResponse();
        cacheService.put(cacheKey, response);
        return new FlightResponse();
    }

    @Override
    public PageResponse<FlightResponse> searchFlight(
            String departureAirportId, String arrivalAirportId,
            LocalDate departureTime,
            int childSeats, int adultSeats,
            int page, int pageSize,
            String sortBy
    ) {
        String cacheKey = "flight_search_" + departureAirportId + "_" + arrivalAirportId + "_"
                + departureTime.toString() + "_"
                + page + "_" + pageSize + "_" + sortBy + "_"
                + "version:" + "_";

        int totalSeats = childSeats + adultSeats;
        Page<Flight> flightPage = flightService.getFlights(
                departureAirportId, arrivalAirportId,
                departureTime,
                totalSeats,
                page, pageSize
        );
        List<Flight> flights = flightPage.getContent();

        flights.forEach(flight -> {
            Money pricePare = flightPriceService.calculateTotalFare(
                    flight,
                    childSeats, adultSeats
            );
            flight.setStandardPrice(pricePare);
        });

        List<FlightResponse> flightResponses = flightMapper.toResponses(flights);

        return new PageResponse<>(
                (int) flightPage.getTotalElements(),
                flightPage.getTotalPages(),
                page,
                flightPage.getSize(),
                flightResponses,
                flightPage.hasNext(),
                flightPage.hasPrevious()
        );
    }

}
