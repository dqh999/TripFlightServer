package com.airline.booking.application.flight;

import com.airline.booking.application.airline.dataTransferObject.response.AirlineResponse;
import com.airline.booking.application.airline.service.IAirlineUseCase;
import com.airline.booking.application.filght.dataTransferObject.request.AddFlightRequest;
import com.airline.booking.application.filght.dataTransferObject.response.FlightResponse;
import com.airline.booking.application.filght.mapper.FlightMapper;
import com.airline.booking.application.filght.service.impl.FlightUseCase;
import com.airline.booking.domain.flight.model.Flight;
import com.airline.booking.domain.flight.service.IFlightService;
import com.airline.booking.domain.utils.valueObject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FlightUseCaseTest {

    @Mock
    private IFlightService flightService;

    @Mock
    private FlightMapper flightMapper;

    @Mock
    private IAirlineUseCase airlineUseCase;

    @InjectMocks
    private FlightUseCase flightUseCase;

    private Flight flight;
    private FlightResponse flightResponse;
    private AirlineResponse airlineResponse;

    private final LocalDateTime dateNow = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        flight = new Flight(
                "flight-id-1",
                "airline-id-1",
                "FL123",
                "SGN",
                dateNow,
                "HAN",
                dateNow.plusHours(2),
                "Test flight",
                new Money(BigDecimal.valueOf(100), "USD"),
                100, null,
                null,
                null
        );

        airlineResponse = new AirlineResponse(
                "airline-id-1",
                "Airline Name",
                "Airline Country",
                "Airline Logo",
                "Airline Status"
        );

        flightResponse = new FlightResponse(
                "flight-id-1",
                airlineResponse,
                "FL123",
                "SGN", dateNow,
                "HAN",
                dateNow.plusHours(2),
                new Money(BigDecimal.valueOf(100), "USD")
        );
    }

    @Test
    void testAddFlight() {
        AddFlightRequest request = new AddFlightRequest(
                "airline-id-1",
                "FL123",
                "SGN", "HAN",
                dateNow, dateNow.plusHours(2),
                "Test flight",
                new Money(BigDecimal.valueOf(100), "USD"),
                100
        );

        when(flightMapper.toFlight(request)).thenReturn(flight);
        when(flightService.create(flight)).thenReturn(flight);
        when(airlineUseCase.get(flight.getAirlineId())).thenReturn(airlineResponse);
        when(flightMapper.toResponse(flight)).thenReturn(flightResponse);

        FlightResponse result = flightUseCase.addFlight(request);

        assertNotNull(result);
        assertEquals("flight-id-1", result.getId());
        assertEquals("FL123", result.getCode());
        assertEquals("SGN", result.getDepartureAirportCode());
        assertEquals("HAN", result.getArrivalAirportCode());
        assertEquals(new Money(BigDecimal.valueOf(100), "USD"), result.getStandardPrice());
        assertNotNull(result.getAirline());  // Ensure airline is correctly set

        verify(flightService, times(1)).create(flight);
        verify(airlineUseCase, times(1)).get(flight.getAirlineId());
        verify(flightMapper, times(1)).toResponse(flight);
    }
}
