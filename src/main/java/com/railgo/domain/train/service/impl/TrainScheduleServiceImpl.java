package com.railgo.domain.train.service.impl;

import com.railgo.domain.train.exception.TrainExceptionCode;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.model.schedule.TrainScheduleStop;
import com.railgo.domain.train.repository.schedule.TrainScheduleRepository;
import com.railgo.domain.train.repository.schedule.TrainScheduleStopRepository;
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

@Service
public class TrainScheduleServiceImpl implements ITrainScheduleService {
    private final TrainScheduleRepository trainScheduleRepository;
    private final TrainScheduleStopRepository trainScheduleStopRepository;

    @Autowired
    public TrainScheduleServiceImpl(TrainScheduleRepository trainScheduleRepository,
                                    TrainScheduleStopRepository trainScheduleStopRepository) {
        this.trainScheduleRepository = trainScheduleRepository;
        this.trainScheduleStopRepository = trainScheduleStopRepository;
    }

    @Override
    public TrainSchedule addSchedule(TrainSchedule trainSchedule) {
        validateSchedule(trainSchedule);

        trainSchedule.setStatus(TrainScheduleStatus.SCHEDULED.getValue());
        trainScheduleRepository.save(trainSchedule);

        return trainSchedule;
    }

    private void validateSchedule(TrainSchedule trainSchedule) {
        TrainScheduleStop trainScheduleStopFirst = trainSchedule.getStops().getFirst();
        LocalDateTime departureTime = trainScheduleStopFirst.getArrivalTime();
        if (departureTime.isBefore(LocalDateTime.now())) {
            throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_INVALID);
        }

        long conflictTimeInMillis = 30 * 60 * 1000;
        for (TrainScheduleStop route : trainSchedule.getStops()) {
            if (hasScheduleConflictAtStation(route.getStationId(), route.getArrivalTime(), conflictTimeInMillis)) {
                throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_CONFLICT);
            }
        }
    }

    private boolean hasScheduleConflictAtStation(String stationId, LocalDateTime time, long conflictTimeInMillis) {
        LocalDateTime startTime = time.minusMinutes(conflictTimeInMillis / (60 * 1000));
        LocalDateTime endTime = time.plusMinutes(conflictTimeInMillis / (60 * 1000));
        return trainScheduleStopRepository.checkConflictingScheduleAtStation(stationId, startTime, endTime);
    }

    @Override
    public TrainSchedule getSchedule(String id) {
        return trainScheduleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_NOT_FOUND));
    }
    @Override
    public TrainSchedule getScheduleByIdAndStations(String id, String departureStationId, String arrivalStationId){
        return trainScheduleRepository.findScheduleByIdAndStations(id,departureStationId,arrivalStationId)
                .orElseThrow(() -> new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_NOT_FOUND));
    }
    @Override
    public Page<TrainSchedule> getAllSchedules(String departureStationId, String arrivalStationId,
                                               LocalDateTime departureTime,
                                               int pageNumber, int pageSize) {
        validateRequestGetAllSchedule(departureStationId, arrivalStationId, departureTime, pageNumber, pageSize);

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        LocalDateTime startDate = calculateStartDate(departureTime);
        LocalDateTime endDate = calculateEndDate(departureTime);

        return trainScheduleRepository.findAllSchedules(departureStationId, arrivalStationId, startDate, endDate, pageable);
    }

    private void validateRequestGetAllSchedule(String departureStationId, String arrivalStationId,
                                               LocalDateTime departureTime,
                                               int pageNumber, int pageSize) {
        if (departureStationId.equals(arrivalStationId) || departureTime.isBefore(LocalDateTime.now())) {
            throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_INVALID);
        }
        if (pageNumber < 1) {
            throw new BusinessException();
        }
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
}
