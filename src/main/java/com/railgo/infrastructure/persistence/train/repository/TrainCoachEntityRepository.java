package com.railgo.infrastructure.persistence.train.repository;

import com.railgo.infrastructure.persistence.train.model.coach.TrainCoachEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainCoachEntityRepository extends JpaRepository<TrainCoachEntity, String> {
}
