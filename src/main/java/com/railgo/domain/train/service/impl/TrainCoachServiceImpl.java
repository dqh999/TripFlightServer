package com.railgo.domain.train.service.impl;

import com.railgo.domain.train.model.coach.TrainCoach;
import com.railgo.domain.train.repository.TrainCoachRepository;
import com.railgo.domain.train.service.ITrainCoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainCoachServiceImpl implements ITrainCoachService {
    private final TrainCoachRepository trainCoachRepository;

    @Autowired
    public TrainCoachServiceImpl(TrainCoachRepository trainCoachRepository) {
        this.trainCoachRepository = trainCoachRepository;
    }

    @Override
    public TrainCoach addTrainCoach(TrainCoach newTrainCoach) {
        trainCoachRepository.save(newTrainCoach);
        return newTrainCoach;
    }

    @Override
    public List<TrainCoach> getAllTrainCoach(String trainId) {
        return List.of();
    }
}
