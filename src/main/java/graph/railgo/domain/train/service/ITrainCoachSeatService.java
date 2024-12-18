package graph.railgo.domain.train.service;

import graph.railgo.domain.train.model.coach.TrainCoachSeat;

import java.util.List;

public interface ITrainCoachSeatService {
    TrainCoachSeat addTrainCoachSeat(TrainCoachSeat newTrainCoachSeat);
    List<TrainCoachSeat> getAllTrainCoachSeat(String coachId);
}
