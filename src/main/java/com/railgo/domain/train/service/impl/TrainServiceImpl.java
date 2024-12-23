package com.railgo.domain.train.service.impl;

import com.railgo.application.train.exception.TrainApplicationExceptionCode;
import com.railgo.domain.train.model.Train;
import com.railgo.domain.train.repository.TrainRepository;
import com.railgo.domain.train.service.ITrainService;
import com.railgo.domain.train.type.TrainStatus;
import com.railgo.domain.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainServiceImpl implements ITrainService {
    private final TrainRepository trainRepository;

    @Autowired
    public TrainServiceImpl(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }
    @Override
    public Train addTrain(Train newTrain) {
        newTrain.setStatus(TrainStatus.ACTIVE.getValue());
        trainRepository.save(newTrain);
        return newTrain;
    }

    @Override
    public Train getTrainById(String id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> new BusinessException(TrainApplicationExceptionCode.TRAIN_NOT_FOUND));
    }
}
