package com.railgo.domain.train.service;

import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.model.schedule.TrainScheduleStop;

import java.time.LocalDateTime;
import java.util.List;

public interface ITrainScheduleStopService {

    void updateAvailableSeats(List<TrainScheduleStop> trainScheduleStops,
                              int seatsBooked);
    /**
     * Rollback the seats that were temporarily reserved for a booking.
     *
     * @param trainScheduleStops the list of train schedule stops affected by the booking.
     * @param seatsToRollback    the number of seats to rollback.
     */
    void rollbackSeats(List<TrainScheduleStop> trainScheduleStops, int seatsToRollback);
    int calculateAvailableSeats(List<TrainScheduleStop> trainScheduleStops);
}
