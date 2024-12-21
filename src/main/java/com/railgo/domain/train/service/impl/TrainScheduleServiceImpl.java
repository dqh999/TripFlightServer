package com.railgo.domain.train.service.impl;

import com.railgo.domain.train.exception.TrainExceptionCode;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.model.schedule.TrainScheduleRoute;
import com.railgo.domain.train.repository.TrainScheduleRepository;
import com.railgo.domain.train.service.ITrainScheduleService;
import com.railgo.domain.train.type.TrainScheduleStatus;
import com.railgo.domain.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class TrainScheduleServiceImpl implements ITrainScheduleService {
    private final TrainScheduleRepository trainScheduleRepository;

    @Autowired
    public TrainScheduleServiceImpl(TrainScheduleRepository trainScheduleRepository) {
        this.trainScheduleRepository = trainScheduleRepository;
    }

    @Override
    public TrainSchedule addSchedule(TrainSchedule trainSchedule) {
        LocalDateTime departureTime = trainSchedule.getDepartureTime();
        if (departureTime.isBefore(LocalDateTime.now())) {
            throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_INVALID);
        }

        long conflictTimeInMillis = 30 * 60 * 1000;

        if (hasScheduleConflict(trainSchedule.getDepartureStationId(), departureTime, conflictTimeInMillis) ||
                hasScheduleConflict(trainSchedule.getArrivalStationId(), trainSchedule.getArrivalTime(), conflictTimeInMillis)) {
            throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_CONFLICT);
        }

        for (TrainScheduleRoute route : trainSchedule.getRoutes()) {
            if (hasScheduleConflict(route.getStationId(), route.getArrivalTime(), conflictTimeInMillis)) {
                throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_CONFLICT);
            }
        }

        trainSchedule.setStatus(TrainScheduleStatus.SCHEDULED.getValue());

        trainScheduleRepository.save(trainSchedule);


        return trainSchedule;
    }

    private boolean hasScheduleConflict(String stationId, LocalDateTime time, long conflictTimeInMillis) {
        LocalDateTime startTime = time.minusMinutes(conflictTimeInMillis / (60 * 1000));
        LocalDateTime endTime = time.plusMinutes(conflictTimeInMillis / (60 * 1000));
        return trainScheduleRepository.checkConflictingSchedules(stationId, startTime, endTime);
    }

    @Override
    public Page<TrainSchedule> getAllSchedules(String departureStationId, String arrivalStationId,
                                               LocalDateTime departureTime,
                                               int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);

        LocalDateTime startDate;
        if (departureTime.toLocalDate().isEqual(LocalDate.now())) {
            startDate = LocalDateTime.now();
        } else {
            startDate = departureTime.toLocalDate().atStartOfDay();
        }
        LocalDateTime endDate = departureTime.toLocalDate().atTime(LocalTime.MAX);

        return trainScheduleRepository.findAllSchedules(departureStationId, arrivalStationId, startDate, endDate, pageable);
    }
}
