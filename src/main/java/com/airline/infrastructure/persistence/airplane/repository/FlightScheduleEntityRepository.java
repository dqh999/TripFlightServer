package com.airline.infrastructure.persistence.airplane.repository;

import com.airline.infrastructure.persistence.airplane.model.FlightEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface FlightScheduleEntityRepository extends JpaRepository<FlightEntity,String> {
    @Query("SELECT s " +
            "FROM FlightEntity s " +
            "WHERE s.departureAirport = :departureAirportId " +
            "AND s.arrivalAirport = :arrivalAirportId " +
            "AND s.departureTime BETWEEN :startDate AND :endDate")
    Page<FlightEntity> findAllSchedules(
            @Param("departureAirportId") String departureAirportId,
            @Param("arrivalAirportId") String arrivalAirportId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
