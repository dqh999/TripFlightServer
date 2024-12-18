package graph.railgo.domain.train.service.impl;

import graph.railgo.application.train.exception.TrainExceptionCode;
import graph.railgo.domain.train.model.Train;
import graph.railgo.domain.train.repository.TrainRepository;
import graph.railgo.domain.train.service.ITrainService;
import graph.railgo.domain.utils.exception.BusinessException;
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
        trainRepository.save(newTrain);
        return newTrain;
    }

    @Override
    public Train getTrainById(String id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> new BusinessException(TrainExceptionCode.TRAIN_NOT_FOUND));
    }
}
