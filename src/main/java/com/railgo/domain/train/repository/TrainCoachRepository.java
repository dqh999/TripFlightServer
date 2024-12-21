package com.railgo.domain.train.repository;

import com.railgo.domain.train.model.coach.TrainCoach;

public interface TrainCoachRepository {
    void save(TrainCoach trainCoach);
}
