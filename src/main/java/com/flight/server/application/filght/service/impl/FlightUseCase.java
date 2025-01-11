package com.flight.server.application.filght.service.impl;


import com.flight.server.application.airline.service.IAirlineUseCase;
import com.flight.server.application.filght.dataTransferObject.request.AddFlightRequest;
import com.flight.server.application.filght.dataTransferObject.response.FlightReservation;
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
import java.util.ArrayList;
import java.util.List;


@Service
public class FlightUseCase implements IFlightUseCase {
    private static final Logger logger = LoggerFactory.getLogger(FlightUseCase.class);

    private final IFlightService flightService;
    private final IFlightPriceService flightPriceService;
    private final IAirlineUseCase airlineUseCase;

    private final FlightMapper flightMapper;
    private final CacheService cacheService;

    @Value("${spring.data.redis.key.flight.detail}")
    private String flightDetailKey;

    public FlightUseCase(
            IFlightService flightService,
            IFlightPriceService flightPriceService,
            IAirlineUseCase airlineUseCase,
            FlightMapper flightMapper,
            CacheService cacheService
    ) {
        this.flightService = flightService;
        this.flightPriceService = flightPriceService;
        this.airlineUseCase = airlineUseCase;
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

    private FlightResponse buildResponse(Flight flight) {
        FlightResponse response = flightMapper.toResponse(flight);

        var airline = airlineUseCase.get(flight.getAirlineId());
        response.setAirline(airline);

        return response;
    }

    @Override
    public FlightReservation getFlight(
            String flightId,
            int childSeats, int adultSeats,
            String sessionId
    ) {
        String cacheKey = flightDetailKey.formatted(flightId);
        if (cacheService.exists(cacheKey)) {
            logger.info("Flight details found in cache for flightId: {}", flightId);

            Flight flightCache = cacheService.get(
                    cacheKey,
                    Flight.class
            );
            return buildFlightReservation(
                    flightCache,
                    childSeats, adultSeats
            );
        }
        logger.info("Flight details querying database for flightId: {}", flightId);
        Flight existFlight = flightService.getById(flightId);

        return buildFlightReservation(
                existFlight,
                childSeats, adultSeats
        );
    }

    private FlightReservation buildFlightReservation(
            Flight flight,
            int childSeats, int adultSeats
    ) {
        FlightResponse flightResponse = buildResponse(flight);
        Money pricePare = calculateTotalFare(
                flight,
                childSeats, adultSeats
        );
        return new FlightReservation(
                flightResponse,
                childSeats, adultSeats,
                pricePare
        );
    }

    private Money calculateTotalFare(
            Flight flight,
            int childSeats, int adultSeats
    ) {
        return flightPriceService.calculateTotalFare(
                flight,
                childSeats, adultSeats
        );
    }

    @Override
    public PageResponse<FlightReservation> searchFlight(
            String departureAirportCode, String arrivalAirportCode,
            LocalDate departureTime,
            int childSeats, int adultSeats,
            int page, int pageSize,
            String sortBy
    ) {
        String cacheKey = "flight_search:"
                + "from:%s_to:%s_depart:%s".formatted(
                departureAirportCode, arrivalAirportCode,
                departureTime.toString()
        );
        int totalSeats = childSeats + adultSeats;
        if (cacheService.exists(cacheKey)) {
            logger.info("Flight details found in cache for flightId: {}", cacheKey);
            List<Flight> flights = cacheService.get(
                    cacheKey,
                    List.class
            );
        }
        Page<Flight> flightPage = flightService.getFlights(
                departureAirportCode, arrivalAirportCode,
                departureTime,
                totalSeats,
                page, pageSize
        );

        List<Flight> flights = flightPage.getContent();

        List<FlightReservation> flightReservations = new ArrayList<>();
        flights.forEach(flight -> flightReservations.add(
                buildFlightReservation(
                        flight,
                        childSeats, adultSeats
                )
        ));

        return new PageResponse<>(
                (int) flightPage.getTotalElements(),
                flightPage.getTotalPages(),
                page,
                flightPage.getSize(),
                flightReservations,
                flightPage.hasNext(),
                flightPage.hasPrevious()
        );
    }

}
