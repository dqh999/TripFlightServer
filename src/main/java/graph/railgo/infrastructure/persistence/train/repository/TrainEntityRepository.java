package graph.railgo.infrastructure.persistence.train.repository;

import graph.railgo.infrastructure.persistence.train.model.TrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainEntityRepository extends JpaRepository<TrainEntity,String> {
}
