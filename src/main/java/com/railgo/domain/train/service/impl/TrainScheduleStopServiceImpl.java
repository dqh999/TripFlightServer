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
                                     String startStation, String endStation,
                                     int seatsBooked) {
        validateUpdateAvailableSeats(trainScheduleStops, startStation, endStation, seatsBooked);

        List<TrainScheduleStop> updatedStops = new ArrayList<>();

        boolean hasStartStation = false;
        for (TrainScheduleStop trainScheduleStop : trainScheduleStops) {
            int availableSeats = trainScheduleStop.getAvailableSeats();
            if (trainScheduleStop.getStationId().equals(startStation)) {
                hasStartStation = true;
                trainScheduleStop.setAvailableSeats(availableSeats - seatsBooked);
                if (trainScheduleStop.getNextStationId().equals(endStation)) {
                    trainScheduleStopRepository.save(trainScheduleStop);
                    return;
                }
                updatedStops.add(trainScheduleStop);
                continue;
            }
            if (trainScheduleStop.getNextStationId().equals(endStation) && hasStartStation) {
                trainScheduleStop.setAvailableSeats(availableSeats - seatsBooked);
                updatedStops.add(trainScheduleStop);
                trainScheduleStopRepository.saveAll(updatedStops);
                return;
            }
            if (hasStartStation) {
                trainScheduleStop.setAvailableSeats(availableSeats - seatsBooked);
                updatedStops.add(trainScheduleStop);
            }
        }
    }

    private void validateUpdateAvailableSeats(List<TrainScheduleStop> trainScheduleStops, String startStation, String endStation, int seatsBooked) {
        if (calculateAvailableSeats(trainScheduleStops,startStation,endStation) < seatsBooked) {
            throw new BusinessException();
        }
    }

    @Override
    public int calculateAvailableSeats(List<TrainScheduleStop> trainScheduleStops,
                                 String startStation, String endStation) {
        int availableSeats = MAX_SEAT;
        boolean hasStartStation = false;
        for (TrainScheduleStop trainScheduleStop : trainScheduleStops) {
            if (hasStartStation) {
                availableSeats = calculateMin(availableSeats, trainScheduleStop.getAvailableSeats());
            }
            if (trainScheduleStop.getStationId().equals(startStation)) {
                availableSeats = trainScheduleStop.getAvailableSeats();
                hasStartStation = true;
            }
            if (trainScheduleStop.getNextStationId().equals(endStation) && hasStartStation) {
                return availableSeats;
            }
        }
        return 0;
    }

    private Integer calculateMin(int x, int y) {
        return Math.min(x, y);
    }
}
