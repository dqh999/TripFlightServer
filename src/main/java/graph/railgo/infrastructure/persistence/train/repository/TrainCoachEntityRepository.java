package graph.railgo.infrastructure.persistence.train.repository;

import graph.railgo.infrastructure.persistence.train.model.coach.TrainCoachEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainCoachEntityRepository extends JpaRepository<TrainCoachEntity, String> {
}
