package com.railgo.domain.train.service.impl;

import com.railgo.domain.train.model.schedule.TrainScheduleStop;
import com.railgo.domain.train.repository.schedule.TrainScheduleStopRepository;
import com.railgo.domain.train.service.ITrainScheduleStopService;
import com.railgo.domain.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainScheduleStopServiceImpl implements ITrainScheduleStopService {
    private static final int MAX_SEAT = 1000000;
    private final TrainScheduleStopRepository trainScheduleStopRepository;

    public TrainScheduleStopServiceImpl(TrainScheduleStopRepository trainScheduleStopRepository) {
        this.trainScheduleStopRepository = trainScheduleStopRepository;
    }

    @Override
    public LocalDateTime getDepartureTime(List<TrainScheduleStop> trainScheduleStops, String startStation) {
        for (TrainScheduleStop trainScheduleStop : trainScheduleStops) {
            if (trainScheduleStop.getStationId().equals(startStation)) {
                return trainScheduleStop.getDepartureTime();
            }
        }
        throw new BusinessException();
    }

    @Override
    public LocalDateTime getArrivalTime(List<TrainScheduleStop> trainScheduleStops,
                                        String endStation) {
        for (TrainScheduleStop trainScheduleStop : trainScheduleStops.reversed()) {
            if (trainScheduleStop.getNextStationId().equals(endStation)) {
                return trainScheduleStop.getArrivalTime();
            }
        }
        throw new BusinessException();
    }

    @Override
    public void updateAvailableSeats(List<TrainScheduleStop> trainScheduleStops,
                                     int seatsBooked) {
        validateUpdateAvailableSeats(trainScheduleStops, seatsBooked);
        for (TrainScheduleStop trainScheduleStop : trainScheduleStops) {
            trainScheduleStop.setAvailableSeats(trainScheduleStop.getAvailableSeats() - seatsBooked);
        }
        trainScheduleStopRepository.saveAll(trainScheduleStops);
    }

    private void validateUpdateAvailableSeats(List<TrainScheduleStop> trainScheduleStops, int seatsBooked) {
        if (calculateAvailableSeats(trainScheduleStops) < seatsBooked) {
            throw new BusinessException();
        }
    }

    @Override
    public int calculateAvailableSeats(List<TrainScheduleStop> trainScheduleStops) {
        int availableSeats = MAX_SEAT;
        for (TrainScheduleStop trainScheduleStop : trainScheduleStops) {
            availableSeats = calculateMin(availableSeats, trainScheduleStop.getAvailableSeats());
        }
        return availableSeats != MAX_SEAT ? availableSeats : 0;
    }

    private Integer calculateMin(int x, int y) {
        return Math.min(x, y);
    }
}
