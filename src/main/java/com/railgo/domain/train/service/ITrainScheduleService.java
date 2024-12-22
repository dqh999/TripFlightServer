package com.railgo.domain.train.service;

import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.model.schedule.TrainScheduleRoute;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface ITrainScheduleService {
    TrainSchedule addSchedule(TrainSchedule trainSchedule);

    TrainSchedule getSchedule(String id);
    Page<TrainSchedule> getAllSchedules(String departureStationId, String arrivalStationId,
                                        LocalDateTime departureTime,
                                        int pageNumber, int pageSize);

    boolean isValidRoute(TrainSchedule schedule,
                         String startStationId, String endStationId);
}
