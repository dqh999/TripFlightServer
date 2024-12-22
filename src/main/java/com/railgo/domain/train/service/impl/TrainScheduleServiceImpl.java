package com.railgo.domain.train.service.impl;

import com.railgo.domain.train.exception.TrainExceptionCode;
import com.railgo.domain.train.model.Train;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.model.schedule.TrainScheduleRoute;
import com.railgo.domain.train.repository.schedule.TrainScheduleRepository;
import com.railgo.domain.train.repository.schedule.TrainScheduleRouteRepository;
import com.railgo.domain.train.service.ITrainScheduleService;
import com.railgo.domain.train.type.TrainScheduleStatus;
import com.railgo.domain.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class TrainScheduleServiceImpl implements ITrainScheduleService {
    private final TrainScheduleRepository trainScheduleRepository;
    private final TrainScheduleRouteRepository trainScheduleRouteRepository;

    @Autowired
    public TrainScheduleServiceImpl(TrainScheduleRepository trainScheduleRepository,
                                    TrainScheduleRouteRepository trainScheduleRouteRepository) {
        this.trainScheduleRepository = trainScheduleRepository;
        this.trainScheduleRouteRepository = trainScheduleRouteRepository;
    }

    @Override
    public TrainSchedule addSchedule(TrainSchedule trainSchedule) {
        validateSchedule(trainSchedule);

        trainSchedule.setStatus(TrainScheduleStatus.SCHEDULED.getValue());

        trainScheduleRepository.save(trainSchedule);


        return trainSchedule;
    }

    private void validateSchedule(TrainSchedule trainSchedule) {
        LocalDateTime departureTime = trainSchedule.getDepartureTime();
        if (departureTime.isBefore(LocalDateTime.now())) {
            throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_INVALID);
        }

        Train train = trainSchedule.getTrain();
        if (trainScheduleRepository.checkConflictingSchedules(train.getId(), departureTime)) {
            throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_CONFLICT);
        }

        long conflictTimeInMillis = 30 * 60 * 1000;

        if (hasScheduleConflictAtStation(trainSchedule.getDepartureStationId(), departureTime, conflictTimeInMillis) ||
                hasScheduleConflictAtStation(trainSchedule.getArrivalStationId(), trainSchedule.getArrivalTime(), conflictTimeInMillis)) {
            throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_CONFLICT);
        }

        for (TrainScheduleRoute route : trainSchedule.getRoutes()) {
            if (hasScheduleConflictAtStation(route.getStationId(), route.getArrivalTime(), conflictTimeInMillis)) {
                throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_CONFLICT);
            }
        }

    }

    private boolean hasScheduleConflictAtStation(String stationId, LocalDateTime time, long conflictTimeInMillis) {
        LocalDateTime startTime = time.minusMinutes(conflictTimeInMillis / (60 * 1000));
        LocalDateTime endTime = time.plusMinutes(conflictTimeInMillis / (60 * 1000));
        return trainScheduleRepository.checkConflictingScheduleAtStation(stationId, startTime, endTime)
                || trainScheduleRepository.checkConflictingScheduleAtStation(stationId, startTime, endTime);
    }

    @Override
    public TrainSchedule getSchedule(String id) {
        return trainScheduleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_NOT_FOUND));
    }

    @Override
    public Page<TrainSchedule> getAllSchedules(String departureStationId, String arrivalStationId,
                                               LocalDateTime departureTime,
                                               int pageNumber, int pageSize) {
        if (departureStationId.equals(arrivalStationId)) {
            throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_INVALID);
        }

        if (departureTime.isBefore(LocalDateTime.now())) {
            throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_INVALID);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        LocalDateTime startDate = calculateStartDate(departureTime);
        LocalDateTime endDate = calculateEndDate(departureTime);

        return trainScheduleRepository.findAllSchedules(departureStationId, arrivalStationId, startDate, endDate, pageable);
    }

    private LocalDateTime calculateStartDate(LocalDateTime departureTime) {
        if (departureTime.toLocalDate().isEqual(LocalDate.now())) {
            return LocalDateTime.now().plusMinutes(30);
        }
        return departureTime.toLocalDate().atStartOfDay();
    }

    private LocalDateTime calculateEndDate(LocalDateTime departureTime) {
        return departureTime.toLocalDate().atTime(LocalTime.MAX);
    }

    @Override
    public boolean isValidRoute(TrainSchedule trainSchedule,
                                String startStationId, String endStationId) {
        if (trainSchedule.getDepartureStationId().equals(startStationId) && trainSchedule.getArrivalStationId().equals(endStationId)) {
            return true;
        }

        List<TrainScheduleRoute> routes = trainSchedule.getRoutes();
        boolean hasStartStation = false;

        for (TrainScheduleRoute route : routes) {
            if (route.getStationId().equals(startStationId) && !hasStartStation) {
                if (route.getStationId().equals(endStationId)) {
                    return true;
                }
                hasStartStation = true;
            } else if (route.getStationId().equals(endStationId) && hasStartStation) {
                return true;
            }
        }
        return false;
    }
}
