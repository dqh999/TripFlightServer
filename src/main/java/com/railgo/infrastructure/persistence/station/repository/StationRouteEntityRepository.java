package com.railgo.infrastructure.persistence.station.repository;

import com.railgo.infrastructure.persistence.station.model.StationRouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StationRouteEntityRepository extends JpaRepository<StationRouteEntity, String> {

    @Query("SELECT r FROM StationRouteEntity r " +
            "WHERE (r.stationA.id = :stationAId AND r.stationB.id = :stationBId) " +
            "   OR (r.stationA.id = :stationBId AND r.stationB.id = :stationAId)")
    Optional<StationRouteEntity> findRouteBetweenStations(
            @Param("stationAId") String stationAId,
            @Param("stationBId") String stationBId);

    @Query("SELECT COUNT(r.id) > 0 FROM StationRouteEntity r " +
            "WHERE (r.stationA.id = :stationAId AND r.stationB.id = :stationBId) " +
            "   OR (r.stationA.id = :stationBId AND r.stationB.id = :stationAId)")
    boolean existDirectRouteBetweenStations(String stationAId, String stationBId);
}
