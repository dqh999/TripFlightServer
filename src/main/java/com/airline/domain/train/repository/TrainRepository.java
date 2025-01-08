package com.railgo.domain.train.repository;

import com.railgo.domain.train.model.Train;

import java.util.Optional;

public interface TrainRepository {
    void save(Train t);
    Optional<Train> findById(String id);
}
