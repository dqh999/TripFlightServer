package com.railgo.application.train.service.impl;

import com.railgo.application.train.dataTransferObject.request.AddTrainRequest;
import com.railgo.application.train.dataTransferObject.response.TrainResponse;
import com.railgo.application.train.mapper.TrainMapper;
import com.railgo.application.train.service.ITrainUseCase;
import com.railgo.domain.train.model.Train;
import com.railgo.domain.train.service.ITrainService;
import com.railgo.infrastructure.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainUseCase implements ITrainUseCase {
    private final ITrainService trainService;
    private final TrainMapper trainMapper;

    @Autowired
    public TrainUseCase(ITrainService trainService,
                        TrainMapper trainMapper) {
        this.trainService = trainService;
        this.trainMapper = trainMapper;
    }

    @Override
    public TrainResponse addTrain(UserDetail userRequest,
                                  AddTrainRequest request) {
        Train trainObject = trainMapper.toTrain(request);
        Train newTrain = trainService.addTrain(trainObject);
        return trainMapper.toDTO(newTrain);
    }
}
