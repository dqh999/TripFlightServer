package com.railgo.infrastructure.persistence.train.repository;

import com.railgo.infrastructure.persistence.train.model.schedule.TrainScheduleRouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TrainScheduleRouteEntityRepository extends JpaRepository<TrainScheduleRouteEntity, String> {
    @Query("SELECT COUNT(s.id) " +
            "FROM TrainScheduleRouteEntity s " +
            "WHERE s.stationId = :stationId " +
            "AND s.arrivalTime BETWEEN :startTime AND :endTime")
    boolean checkConflictingScheduleAtStation(@Param("stationId") String stationId,
                                              @Param("startTime") LocalDateTime startTime,
                                              @Param("endTime") LocalDateTime endTime);
}
