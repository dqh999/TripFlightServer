package com.railgo.domain.train.service;

import com.railgo.domain.train.model.coach.TrainCoach;

import java.util.List;

public interface ITrainCoachService {
    TrainCoach addTrainCoach(TrainCoach newTrainCoach);
    List<TrainCoach> getAllTrainCoach(String trainId);
}
