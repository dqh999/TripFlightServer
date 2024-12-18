package graph.railgo.domain.train.service;

import graph.railgo.domain.train.model.schedule.TrainSchedule;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface ITrainScheduleService {
    TrainSchedule addSchedule(TrainSchedule trainSchedule);
    Page<TrainSchedule> getAllSchedules(String departureStationId, String arrivalStationId,
                                        LocalDateTime departureTime, LocalDateTime arrivalTime,
                                        int pageNumber, int pageSize);
}
