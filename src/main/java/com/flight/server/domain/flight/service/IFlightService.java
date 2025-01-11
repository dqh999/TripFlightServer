package com.flight.server.domain.flight.service;

import com.flight.server.domain.flight.model.Flight;
import com.flight.server.domain.utils.service.GenericService;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface IFlightService extends GenericService<Flight, String> {
    Flight updateAvailableSeats(Flight flight, int newSeatBooking);

    Flight rollbackBookedSeats(Flight flight, int canceledSeats);

    Page<Flight> getFlights(
            String departureAirportCode, String arrivalAirportCode,
            LocalDate departureTime,
            Integer totalSeats,
            int pageNumber, int pageSize
    );
}
