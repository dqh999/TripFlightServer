package com.railgo.domain.train.service;

import com.railgo.domain.train.model.schedule.TrainScheduleStop;

import java.util.List;

public interface ITrainScheduleStopService {
    void updateAvailableSeats(List<TrainScheduleStop> trainScheduleStops,
                              String startStation, String endStation,
                              int seatsBooked);
    int calculateAvailableSeats(List<TrainScheduleStop> trainScheduleStops,
                          String startStation, String endStation);
}
