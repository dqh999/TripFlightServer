package com.railgo.infrastructure.persistence.train.repository;

import com.railgo.infrastructure.persistence.train.model.schedule.TrainScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TrainScheduleEntityRepository extends JpaRepository<TrainScheduleEntity,String> {
    @Query("SELECT DISTINCT s " +
            "FROM TrainScheduleEntity s " +
            "   JOIN TrainScheduleStopEntity st1 ON s.id = st1.scheduleId " +
            "   JOIN TrainScheduleStopEntity st2 ON s.id = st2.scheduleId " +
            "WHERE s.status IN ('PENDING', 'SCHEDULED', 'IN_PROGRESS') " +
            "   AND st1.arrivalTime < st2.arrivalTime " +
            "   AND st1.stationId = :departureStationId " +
            "   AND st2.stationId = :arrivalStationId " +
            "   AND st1.arrivalTime BETWEEN :startDate AND :endDate")
    Page<TrainScheduleEntity> findAllSchedules(
            @Param("departureStationId") String departureStationId,
            @Param("arrivalStationId") String arrivalStationId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    @Query("SELECT s " +
            "FROM TrainScheduleEntity s " +
            "   JOIN TrainScheduleStopEntity st1 ON s.id = st1.scheduleId " +
            "   JOIN TrainScheduleStopEntity st2 ON s.id = st2.scheduleId " +
            "WHERE s.id = :trainScheduleId " +
            "   AND st1.arrivalTime < st2.arrivalTime " +
            "   AND st1.stationId = :departureStationId " +
            "   AND st2.stationId = :arrivalStationId")
    Optional<TrainScheduleEntity> findScheduleByIdAndStations(String trainScheduleId,
                                                    @Param("departureStationId") String departureStationId,
                                                    @Param("arrivalStationId") String arrivalStationId);
}
