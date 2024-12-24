package com.railgo.domain.train.service;

import com.railgo.domain.train.model.schedule.TrainSchedule;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface ITrainScheduleService {
    TrainSchedule addSchedule(TrainSchedule trainSchedule);

    TrainSchedule getScheduleByIdAndStations(String id, String departureStationId, String arrivalStationId);
    Page<TrainSchedule> getAllSchedules(String departureStationId, String arrivalStationId,
                                        LocalDateTime departureTime,
                                        int pageNumber, int pageSize);
}
