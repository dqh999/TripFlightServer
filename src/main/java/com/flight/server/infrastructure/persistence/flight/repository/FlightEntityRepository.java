package com.flight.server.infrastructure.persistence.flight.repository;

import com.flight.server.infrastructure.persistence.flight.model.FlightEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface FlightEntityRepository extends JpaRepository<FlightEntity,String> {
    boolean existsByCode(String code);
    @Query("SELECT s " +
            "FROM FlightEntity s " +
            "WHERE s.departureAirportCode = :departureAirportCode " +
            "AND s.arrivalAirportCode = :arrivalAirportCode " +
            "AND s.availableSeats >= :totalSeats " +
            "AND s.departureTime BETWEEN :startDate AND :endDate")
    Page<FlightEntity> findFlights(
            @Param("departureAirportCode") String departureAirportCode,
            @Param("arrivalAirportCode") String arrivalAirportCode,
            @Param("totalSeats") Integer totalSeats,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
