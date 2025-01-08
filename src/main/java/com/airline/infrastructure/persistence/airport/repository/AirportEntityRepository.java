package com.airline.infrastructure.persistence.airport.repository;

import com.airline.infrastructure.persistence.airport.model.AirportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AirportEntityRepository extends JpaRepository<AirportEntity,String> {

    @Query("SELECT s FROM AirportEntity s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<AirportEntity> searchByKeyword(String keyword, Pageable pageable);
}
