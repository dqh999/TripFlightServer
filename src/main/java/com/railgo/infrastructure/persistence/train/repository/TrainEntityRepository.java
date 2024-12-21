package com.railgo.infrastructure.persistence.train.repository;

import com.railgo.infrastructure.persistence.train.model.TrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainEntityRepository extends JpaRepository<TrainEntity,String> {
}
