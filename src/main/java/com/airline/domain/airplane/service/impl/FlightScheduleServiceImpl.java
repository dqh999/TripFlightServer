package com.airline.domain.airplane.service.impl;

import com.airline.domain.airplane.exception.FlightExceptionCode;
import com.airline.domain.airplane.model.Flight;
import com.airline.domain.airplane.repository.FlightScheduleRepository;
import com.airline.domain.airplane.service.IFlightScheduleService;
import com.airline.domain.airplane.type.FlightScheduleStatus;
import com.airline.domain.utils.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Service
public class FlightScheduleServiceImpl implements IFlightScheduleService {
    private final FlightScheduleRepository flightScheduleRepository;

    public FlightScheduleServiceImpl(FlightScheduleRepository flightScheduleRepository) {
        this.flightScheduleRepository = flightScheduleRepository;
    }

    @Override
    public Flight addSchedule(Flight flight) {
        validateSchedule(flight);

        flight.setStatus(FlightScheduleStatus.SCHEDULED.getValue());
        flightScheduleRepository.save(flight);

        return flight;
    }

    private void validateSchedule(Flight flight) {

    }

    @Override
    public Flight getSchedule(String id) {
        return flightScheduleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(FlightExceptionCode.FLIGHT_SCHEDULE_NOT_FOUND));
    }

    @Override
    public Page<Flight> getAllSchedules(String departureAirportId, String arrivalAirportId,
                                        LocalDateTime departureTime,
                                        int pageNumber, int pageSize) {
        validateRequestGetAllSchedule(departureAirportId, arrivalAirportId, departureTime, pageNumber, pageSize);

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        LocalDateTime startDate = calculateStartDate(departureTime);
        LocalDateTime endDate = calculateEndDate(departureTime);

        Page<Flight> flightSchedulePage = flightScheduleRepository.findAllSchedules(departureAirportId, arrivalAirportId, startDate, endDate, pageable);

        return new PageImpl<>(
                flightSchedulePage.getContent(),
                flightSchedulePage.getPageable(),
                flightSchedulePage.getTotalElements()
        );
    }

    private void validateRequestGetAllSchedule(String departureAirportId, String arrivalAirportId,
                                               LocalDateTime departureTime,
                                               int pageNumber, int pageSize) {
        if (departureAirportId.equals(arrivalAirportId) || departureTime.isBefore(LocalDateTime.now())) {
            throw new BusinessException(FlightExceptionCode.FLIGHT_SCHEDULE_INVALID_PARAM);
        }
        if (pageNumber < 1) {
            throw new BusinessException(FlightExceptionCode.FLIGHT_SCHEDULE_INVALID_PARAM);
        }
    }

    private LocalDateTime calculateStartDate(LocalDateTime departureTime) {
        if (departureTime.toLocalDate().isEqual(LocalDate.now())) {
            return LocalDateTime.now().plusMinutes(30);
        }
        return departureTime.toLocalDate().atStartOfDay();
    }

    private LocalDateTime calculateEndDate(LocalDateTime departureTime) {
        return departureTime.toLocalDate().atTime(LocalTime.MAX);
    }

}
