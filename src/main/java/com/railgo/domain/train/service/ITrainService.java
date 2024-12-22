package com.railgo.domain.train.service;

import com.railgo.domain.train.model.Train;

public interface ITrainService {
    Train addTrain(Train newTrain);
    Train getTrainById(String id);

    void reserveSeats(Train train, int numberOfSeats);
    void cancelSeats(Train train, int numberOfSeats);
}
