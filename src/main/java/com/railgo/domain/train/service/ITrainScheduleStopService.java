package com.railgo.domain.train.service;

import com.railgo.domain.train.model.schedule.TrainScheduleStop;

import java.time.LocalDateTime;
import java.util.List;

public interface ITrainScheduleStopService {
    LocalDateTime getDepartureTime(List<TrainScheduleStop> trainScheduleStops,
                                   String startStation);

    LocalDateTime getArrivalTime(List<TrainScheduleStop> trainScheduleStops,
                                 String endStation);

    void updateAvailableSeats(List<TrainScheduleStop> trainScheduleStops,
                              int seatsBooked);

    int calculateAvailableSeats(List<TrainScheduleStop> trainScheduleStops);
}
