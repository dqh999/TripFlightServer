package com.railgo.infrastructure.persistence.train.repository;

import com.railgo.infrastructure.persistence.train.model.schedule.TrainScheduleStopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TrainScheduleStopEntityRepository extends JpaRepository<TrainScheduleStopEntity, String> {
    @Query("SELECT COUNT(s.id) > 0 " +
            "FROM TrainScheduleStopEntity s " +
            "WHERE (s.stationId = :stationId AND s.departureTime BETWEEN :startTime AND :endTime) " +
            "   OR (s.nextStationId = :stationId AND s.arrivalTime BETWEEN :startTime AND :endTime)")
    boolean checkConflictingScheduleAtStation(@Param("stationId") String stationId,
                                              @Param("startTime") LocalDateTime startTime,
                                              @Param("endTime") LocalDateTime endTime);
}
