package graph.railgo.infrastructure.persistence.train.repository;

import graph.railgo.infrastructure.persistence.train.model.schedule.TrainScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
