package com.railgo.domain.train.service.impl;

import com.railgo.domain.train.model.schedule.TrainScheduleStop;
import com.railgo.domain.train.repository.schedule.TrainScheduleStopRepository;
import com.railgo.domain.train.service.ITrainScheduleStopService;
import com.railgo.domain.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainScheduleStopServiceImpl implements ITrainScheduleStopService {
    private final TrainScheduleStopRepository trainScheduleStopRepository;

    public TrainScheduleStopServiceImpl(TrainScheduleStopRepository trainScheduleStopRepository) {
        this.trainScheduleStopRepository = trainScheduleStopRepository;
    }

    @Override
    public void updateAvailableSeats(List<TrainScheduleStop> trainScheduleStops,
                                     String startStation, String endStation,
                                     int seatsBooked) {
        List<TrainScheduleStop> updatedStops = new ArrayList<>();

        validateUpdateAvailableSeats(trainScheduleStops, startStation, endStation, seatsBooked);
        boolean hasStartStation = false;
        for (TrainScheduleStop trainScheduleStop : trainScheduleStops) {
            if (trainScheduleStop.getStationId().equals(startStation)) {
                hasStartStation = true;
                trainScheduleStop.setAvailableSeats(trainScheduleStop.getAvailableSeats() - seatsBooked);
                updatedStops.add(trainScheduleStop);
                continue;
            }
            if (trainScheduleStop.getStationId().equals(endStation)) {
                break;
            }
            if (hasStartStation) {
                trainScheduleStop.setAvailableSeats(trainScheduleStop.getAvailableSeats() - seatsBooked);
                updatedStops.add(trainScheduleStop);
            }
        }
        trainScheduleStopRepository.saveAll(updatedStops);
    }

    private void validateUpdateAvailableSeats(List<TrainScheduleStop> trainScheduleStops, String startStation, String endStation, int seatsBooked) {
        boolean hasStartStation = false;
        for (TrainScheduleStop trainScheduleStop : trainScheduleStops) {
            if (trainScheduleStop.getStationId().equals(startStation)) {
                hasStartStation = true;
                if (trainScheduleStop.getAvailableSeats() < seatsBooked) {
                    throw new BusinessException();
                }
                continue;
            }
            if (trainScheduleStop.getStationId().equals(endStation)) {
                return;
            }
            if (hasStartStation) {
                if (trainScheduleStop.getAvailableSeats() < seatsBooked) {
                    throw new BusinessException();
                }
            }
        }
    }

    @Override
    public int calculateAvailableSeats(List<TrainScheduleStop> trainScheduleStops,
                                 String startStation, String endStation) {
        int availableSeats = 0;
        for (TrainScheduleStop trainScheduleStop : trainScheduleStops) {
            if (trainScheduleStop.getStationId().equals(startStation)) {
                availableSeats = trainScheduleStop.getAvailableSeats();
                continue;
            }
            if (trainScheduleStop.getStationId().equals(endStation)) {
                return availableSeats;
            }
            availableSeats = calculateMin(availableSeats, trainScheduleStop.getAvailableSeats());
        }
        return 0;
    }

    private Integer calculateMin(int x, int y) {
        return Math.min(x, y);
    }
}
