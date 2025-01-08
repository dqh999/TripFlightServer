package com.railgo.application.train.service;

import com.railgo.application.train.dataTransferObject.request.AddTrainRequest;
import com.railgo.application.train.dataTransferObject.response.TrainResponse;
import com.railgo.infrastructure.security.UserDetail;

public interface ITrainUseCase {
    TrainResponse addTrain(UserDetail userRequest,
                           AddTrainRequest request);
}
