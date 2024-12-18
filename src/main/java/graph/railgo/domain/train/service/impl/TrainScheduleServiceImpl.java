package graph.railgo.domain.train.service.impl;

import graph.railgo.domain.train.model.schedule.TrainSchedule;
import graph.railgo.domain.train.repository.TrainScheduleRepository;
import graph.railgo.domain.train.service.ITrainScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrainScheduleServiceImpl implements ITrainScheduleService {
    private final TrainScheduleRepository trainScheduleRepository;

    @Autowired
    public TrainScheduleServiceImpl(TrainScheduleRepository trainScheduleRepository) {
        this.trainScheduleRepository = trainScheduleRepository;
    }

    @Override
    public TrainSchedule addSchedule(TrainSchedule trainSchedule) {

        trainScheduleRepository.save(trainSchedule);
        return trainSchedule;
    }

    @Override
    public Page<TrainSchedule> getAllSchedules(String departureStationId, String arrivalStationId,
                                               LocalDateTime departureTime, LocalDateTime arrivalTime,
                                               int pageNumber, int pageSize) {
        return null;
    }
}
