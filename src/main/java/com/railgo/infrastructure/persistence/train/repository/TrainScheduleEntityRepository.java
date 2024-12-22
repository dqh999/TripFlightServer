package com.railgo.infrastructure.persistence.train.repository;

import com.railgo.infrastructure.persistence.train.model.schedule.TrainScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TrainScheduleEntityRepository extends JpaRepository<TrainScheduleEntity,String> {
    @Query("SELECT COUNT(s.id) > 0 " +
            "FROM TrainScheduleEntity s " +
            "WHERE (s.departureStationId = :stationId AND s.departureTime BETWEEN :startTime AND :endTime) " +
            "OR (s.arrivalStationId = :stationId AND s.arrivalTime BETWEEN :startTime AND :endTime) ")
    boolean checkConflictingScheduleAtStation(@Param("stationId") String stationId,
                                              @Param("startTime") LocalDateTime startTime,
                                              @Param("endTime") LocalDateTime endTime);

    @Query("SELECT COUNT(s.id) > 0 FROM TrainScheduleEntity s " +
            "WHERE s.train.id = :trainId AND s.arrivalTime < :departureTime")
    boolean checkConflictingSchedule(String trainId, LocalDateTime departureTime);

    @Query("SELECT DISTINCT s FROM TrainScheduleEntity s " +
            "JOIN TrainScheduleRouteEntity sr1 ON s.id = sr1.scheduleId " +
            "JOIN TrainScheduleRouteEntity sr2 ON s.id = sr2.scheduleId " +
            "WHERE s.status IN ('PENDING', 'SCHEDULED', 'IN_PROGRESS') AND ( (" +
            "    s.departureStationId = :departureStationId " +
            "    AND s.departureTime BETWEEN :startDate AND :endDate " +
            "    AND (s.arrivalStationId = :arrivalStationId OR sr1.stationId = :arrivalStationId) " +
            ") " +
            "OR (" +
            "    sr1.stationId = :departureStationId " +
            "   AND sr1.arrivalTime BETWEEN :startDate AND :endDate " +
            "    AND (s.arrivalStationId = :arrivalStationId " +
            "       OR (sr1.arrivalTime < sr2.arrivalTime AND sr2.stationId = :arrivalStationId)) " +
            ") )")
    Page<TrainScheduleEntity> findAllSchedules(
            @Param("departureStationId") String departureStationId,
            @Param("arrivalStationId") String arrivalStationId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
