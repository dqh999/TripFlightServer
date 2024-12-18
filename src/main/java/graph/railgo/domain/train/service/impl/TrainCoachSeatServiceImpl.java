package graph.railgo.domain.train.service.impl;

import graph.railgo.domain.train.model.coach.TrainCoachSeat;
import graph.railgo.domain.train.service.ITrainCoachSeatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainCoachSeatServiceImpl implements ITrainCoachSeatService {
    @Override
    public TrainCoachSeat addTrainCoachSeat(TrainCoachSeat newTrainCoachSeat) {
        return null;
    }

    @Override
    public List<TrainCoachSeat> getAllTrainCoachSeat(String coachId) {
        return List.of();
    }
}
