package com.flight.server.infrastructure.persistence.flight.repository;

import com.flight.server.infrastructure.persistence.flight.model.FlightEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface FlightEntityRepository extends JpaRepository<FlightEntity,String> {
    @Query("SELECT s " +
            "FROM FlightEntity s " +
            "JOIN FlightSeatEntity st " +
            "   ON s.id = st.flightId " +
            "WHERE s.departureAirportId = :departureAirportId " +
            "AND s.arrivalAirportId = :arrivalAirportId " +
            "AND s.departureTime BETWEEN :startDate AND :endDate")
    Page<FlightEntity> findFlights(
            @Param("departureAirportId") String departureAirportId,
            @Param("arrivalAirportId") String arrivalAirportId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
