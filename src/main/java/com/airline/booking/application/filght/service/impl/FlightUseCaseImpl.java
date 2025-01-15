package com.airline.booking.application.filght.service.impl;


import com.airline.booking.application.airline.service.IAirlineUseCase;
import com.airline.booking.application.filght.dataTransferObject.request.AddFlightRequest;
import com.airline.booking.application.filght.dataTransferObject.response.FlightReservation;
import com.airline.booking.application.filght.dataTransferObject.response.FlightResponse;
import com.airline.booking.application.filght.mapper.FlightMapper;
import com.airline.booking.application.filght.service.IFlightUseCase;
import com.airline.booking.application.utils.PageResponse;
import com.airline.booking.application.component.KafkaProducer;
import com.airline.booking.domain.flight.model.Flight;
import com.airline.booking.domain.flight.service.IFlightPriceService;
import com.airline.booking.domain.flight.service.IFlightService;
import com.airline.booking.domain.utils.valueObject.Money;
import com.airline.booking.infrastructure.service.cache.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class FlightUseCaseImpl implements IFlightUseCase {
    private static final Logger logger = LoggerFactory.getLogger(FlightUseCaseImpl.class);

    private final IFlightService flightService;
    private final IFlightPriceService flightPriceService;
    private final IAirlineUseCase airlineUseCase;

    private final FlightMapper flightMapper;

    private final CacheService cacheService;
    private final KafkaProducer kafkaProducer;

    @Value("${spring.data.redis.key.flight.detail}")
    private String flightDetailKey;

    public FlightUseCaseImpl(
            IFlightService flightService,
            IFlightPriceService flightPriceService,
            IAirlineUseCase airlineUseCase,
            FlightMapper flightMapper,
            CacheService cacheService,
            KafkaProducer kafkaProducer
    ) {
        this.flightService = flightService;
        this.flightPriceService = flightPriceService;
        this.airlineUseCase = airlineUseCase;
        this.flightMapper = flightMapper;
        this.cacheService = cacheService;
        this.kafkaProducer = kafkaProducer;
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
        Flight existFlight;
        if (cacheService.exists(cacheKey)) {
            logger.info("Flight found in cache for flightId: {}", flightId);
            existFlight = cacheService.get(
                    cacheKey,
                    Flight.class
            );
        } else {
            logger.info("Flight querying database for flightId: {}", flightId);
            existFlight = flightService.getById(flightId);
        }

        kafkaProducer.send("flight_get_", existFlight);

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
            Page<Flight> flightPage = cacheService.get(
                    cacheKey,
                    Page.class
            );
            List<FlightReservation> flightReservations = new ArrayList<>();
            flightPage.getContent().forEach(flight -> flightReservations.add(
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
