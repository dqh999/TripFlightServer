package com.railgo.infrastructure.persistence.train.repository;

import com.railgo.infrastructure.persistence.train.model.schedule.TrainScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainScheduleEntityRepository extends JpaRepository<TrainScheduleEntity,String> {
    @Query("SELECT s FROM TrainScheduleEntity s " +
            "JOIN TrainScheduleRouteEntity sr ON (s.departureStationId = sr.stationId OR s.arrivalStationId = sr.stationId) " +
            "WHERE (s.departureStationId = :stationId AND s.departureTime BETWEEN :startTime AND :endTime) " +
            "OR (s.arrivalStationId = :stationId AND s.arrivalTime BETWEEN :startTime AND :endTime) " +
            "OR (sr.arrivalTime BETWEEN :startTime AND :endTime)")
    List<TrainScheduleEntity> findConflictingSchedules(String stationId,
                                                       LocalDateTime startTime, LocalDateTime endTime);
    @Query("SELECT  COUNT(s.id) > 0 FROM TrainScheduleEntity s " +
            "JOIN TrainScheduleRouteEntity sr ON (s.departureStationId = sr.stationId OR s.arrivalStationId = sr.stationId) " +
            "WHERE (s.departureStationId = :stationId AND s.departureTime BETWEEN :startTime AND :endTime) " +
            "OR (s.arrivalStationId = :stationId AND s.arrivalTime BETWEEN :startTime AND :endTime) " +
            "OR (sr.arrivalTime BETWEEN :startTime AND :endTime)")
    boolean checkConflictingSchedules(String stationId, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT DISTINCT s FROM TrainScheduleEntity s " +
            "JOIN TrainScheduleRouteEntity sr1 ON s.id = sr1.scheduleId " +
            "JOIN TrainScheduleRouteEntity sr2 ON s.id = sr2.scheduleId " +
            "WHERE (" +
            "    s.departureStationId = :departureStationId " +
            "    AND s.departureTime BETWEEN :startDate AND :endDate " +
            "    AND (s.arrivalStationId = :arrivalStationId OR sr1.stationId = :arrivalStationId)" +
            ") " +
            "OR (" +
            "    sr1.stationId = :departureStationId " +
            "    AND sr1.arrivalTime BETWEEN :startDate AND :endDate " +
            "    AND (s.arrivalStationId = :arrivalStationId OR sr2.stationId = :arrivalStationId)" +
            ")")
    Page<TrainScheduleEntity> findAllSchedules(
            @Param("departureStationId") String departureStationId,
            @Param("arrivalStationId") String arrivalStationId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
