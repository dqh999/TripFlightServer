package com.railgo.infrastructure.persistence.station.repository;

import com.railgo.infrastructure.persistence.station.model.StationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StationEntityRepository extends JpaRepository<StationEntity,String> {

    @Query("SELECT s FROM StationEntity s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<StationEntity> searchByKeyword(String keyword, Pageable pageable);
}
