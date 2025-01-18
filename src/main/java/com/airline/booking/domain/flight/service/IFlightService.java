package com.airline.booking.domain.flight.service;

import com.airline.booking.domain.flight.model.Flight;
import com.airline.booking.domain.utils.service.CURDService;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface IFlightService extends CURDService<Flight, String> {
    Flight updateAvailableSeats(Flight flight, int newSeatBooking);

    Flight rollbackBookedSeats(Flight flight, int canceledSeats);

    Page<Flight> getFlights(
            String departureAirportCode, String arrivalAirportCode,
            LocalDate departureTime,
            Integer totalSeats,
            int pageNumber, int pageSize
    );
}
