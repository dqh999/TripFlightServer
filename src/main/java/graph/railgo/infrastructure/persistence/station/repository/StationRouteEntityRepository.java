package graph.railgo.infrastructure.persistence.station.repository;

import graph.railgo.infrastructure.persistence.station.model.StationRouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRouteEntityRepository extends JpaRepository<StationRouteEntity,String> {
}
