package graph.railgo.infrastructure.persistence.station.repository;

import graph.railgo.infrastructure.persistence.station.model.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StationEntityRepository extends JpaRepository<StationEntity,String> {
    Optional<StationEntity> findById(String id);
}
