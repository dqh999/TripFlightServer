package com.airline.booking.infrastructure.persistence.airport.repository;

import com.airline.booking.infrastructure.persistence.airport.model.AirportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AirportEntityRepository extends JpaRepository<AirportEntity,String> {
    Optional<AirportEntity> findByCode(String code);
    @Query("SELECT s FROM AirportEntity s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.city) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.country) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<AirportEntity> searchByKeyword(String keyword, Pageable pageable);
    boolean existsByCode(String code);
}
