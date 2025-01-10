package com.flight.server.domain.flight.service.impl;

import com.flight.server.domain.flight.excpetion.FlightExceptionCode;
import com.flight.server.domain.flight.model.Flight;
import com.flight.server.domain.flight.repository.FlightRepository;
import com.flight.server.domain.flight.service.IFlightService;
import com.flight.server.domain.flight.type.FlightStatus;
import com.flight.server.domain.utils.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class FlightServiceImpl implements IFlightService {
    private final FlightRepository flightRepository;

    public FlightServiceImpl(
            FlightRepository flightRepository
    ) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight create(Flight flight) {
        validateFlight(flight);

        flight.setAvailableSeats(flight.getTotalSeats());
        flight.setStatus(FlightStatus.SCHEDULED.getValue());

        return flightRepository.save(flight);
    }

    private void validateFlight(Flight flight) {
        if (!flightRepository.existByCode(flight.getCode())) {
            throw new BusinessException(FlightExceptionCode.FLIGHT_NOT_FOUND);
        }
    }

    @Override
    public Flight updateAvailableSeats(
            Flight flight,
            int newSeatBooking
    ) {
        int newAvailableSeats = flight.getAvailableSeats() + newSeatBooking;
        flight.setAvailableSeats(newAvailableSeats);
        return flightRepository.save(flight);
    }

    @Override
    public Flight rollbackBookedSeats(
            Flight flight,
            int canceledSeats
    ) {
        int newAvailableSeats = flight.getAvailableSeats() - canceledSeats;
        flight.setAvailableSeats(newAvailableSeats);
        return flightRepository.save(flight);
    }

    @Override
    public Flight update(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Flight getById(String id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new BusinessException(FlightExceptionCode.FLIGHT_NOT_FOUND));
    }

    @Override
    public Page<Flight> getFlights(
            String departureAirportId, String arrivalAirportId,
            LocalDate departureTime,
            Integer totalSeats,
            int pageNumber, int pageSize
    ) {
        validateGetFlights(
                departureAirportId, arrivalAirportId,
                departureTime,
                totalSeats,
                pageNumber, pageSize
        );
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        LocalDateTime startDate = calculateStartDate(departureTime);
        LocalDateTime endDate = calculateEndDate(departureTime);
        return flightRepository.findFlights(
                departureAirportId, arrivalAirportId,
                totalSeats,
                startDate, endDate,
                pageable
        );
    }

    private void validateGetFlights(
            String departureAirportId, String arrivalAirportId,
            LocalDate departureTime,
            Integer totalSeats,
            int pageNumber, int pageSize
    ) {
        if (departureAirportId == null || departureAirportId.isBlank()
                || arrivalAirportId == null || arrivalAirportId.isBlank()
                || departureAirportId.equals(arrivalAirportId)
        ) {
            throw new BusinessException(FlightExceptionCode.FLIGHT_NOT_FOUND);
        }
        if (departureTime.isBefore(LocalDate.now())) {
            throw new BusinessException();
        }
        if (totalSeats < 0 || totalSeats > 100) {
            throw new BusinessException();
        }
        if (pageNumber < 1 || pageSize < 1) {
            throw new BusinessException(FlightExceptionCode.FLIGHT_NOT_FOUND);
        }
    }

    private LocalDateTime calculateStartDate(LocalDate departureTime) {
        if (departureTime.equals(LocalDate.now())) {
            return LocalDateTime.now().plusMinutes(30);
        }

        return departureTime.atStartOfDay();
    }

    private LocalDateTime calculateEndDate(LocalDate departureTime) {
        return departureTime.atTime(23, 59, 59);
    }

    @Override
    public void delete(Flight flight) {

    }

}
