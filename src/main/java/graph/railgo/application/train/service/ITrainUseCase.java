package graph.railgo.application.train.service;

import graph.railgo.application.train.dataTransferObject.request.AddTrainRequest;
import graph.railgo.application.train.dataTransferObject.response.TrainResponse;
import graph.railgo.infrastructure.security.UserDetail;

public interface ITrainUseCase {
    TrainResponse addTrain(UserDetail userRequest,
                           AddTrainRequest request);
}
