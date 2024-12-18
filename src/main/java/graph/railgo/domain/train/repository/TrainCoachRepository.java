package graph.railgo.domain.train.repository;

import graph.railgo.domain.train.model.coach.TrainCoach;

public interface TrainCoachRepository {
    void save(TrainCoach trainCoach);
}
