package graph.railgo.domain.train.service;

import graph.railgo.domain.train.model.Train;

public interface ITrainService {
    Train addTrain(Train newTrain);
    Train getTrainById(String id);
}
