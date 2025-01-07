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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
        LocalDateTime departureTime = trainScheduleStopFirst.getDepartureTime();
        if (departureTime.isBefore(LocalDateTime.now())) {
            throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_INVALID);
        }
        long conflictTimeInMillis = 30 * 60 * 1000;
        if (hasScheduleConflictAtStation(trainScheduleStopFirst.getStationId(), trainScheduleStopFirst.getDepartureTime(), conflictTimeInMillis)) {
            throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_CONFLICT);
        }

        for (TrainScheduleStop route : trainSchedule.getStops()) {
            if (hasScheduleConflictAtStation(route.getNextStationId(), route.getArrivalTime(), conflictTimeInMillis)) {
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
    public TrainSchedule getScheduleByIdAndStations(String id, String departureStationId, String arrivalStationId) {
        TrainSchedule existTrainSchedule = trainScheduleRepository.findScheduleByIdAndStations(id, departureStationId, arrivalStationId)
                .orElseThrow(() -> new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_NOT_FOUND));
        filterTrainSchedule(existTrainSchedule, departureStationId, arrivalStationId);
        return existTrainSchedule;
    }

    private void filterTrainSchedule(TrainSchedule trainSchedule,
                                     String departureStationId, String arrivalStationId) {
        List<TrainScheduleStop> trainScheduleStops = trainSchedule.getStops();

        if (trainScheduleStops.getFirst().getStationId().equals(departureStationId)
                && trainScheduleStops.getLast().getNextStationId().equals(arrivalStationId)) {
            return;
        }

        boolean hasDepartureStation = false;
        List<TrainScheduleStop> filteredStops = new ArrayList<>();

        for (TrainScheduleStop trainScheduleStop : trainScheduleStops) {
            if (!hasDepartureStation && trainScheduleStop.getStationId().equals(departureStationId)) {
                hasDepartureStation = true;
            }

            if (hasDepartureStation) {
                filteredStops.add(trainScheduleStop);

                if (trainScheduleStop.getNextStationId().equals(arrivalStationId)) break;
            }
        }

        if (filteredStops.isEmpty()) return;

        for (int i = 0; i < filteredStops.size(); i++) {
            filteredStops.get(i).setStopOrder(i);
        }
        trainSchedule.setStops(filteredStops);
    }

    @Override
    public Page<TrainSchedule> getAllSchedules(String departureStationId, String arrivalStationId,
                                               LocalDateTime departureTime,
                                               int pageNumber, int pageSize) {
        validateRequestGetAllSchedule(departureStationId, arrivalStationId, departureTime, pageNumber, pageSize);

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        LocalDateTime startDate = calculateStartDate(departureTime);
        LocalDateTime endDate = calculateEndDate(departureTime);

        Page<TrainSchedule> trainSchedulePage = trainScheduleRepository.findAllSchedules(departureStationId, arrivalStationId, startDate, endDate, pageable);
        List<TrainSchedule> trainSchedules = trainSchedulePage.getContent();

        trainSchedules.forEach(trainSchedule -> filterTrainSchedule(trainSchedule, departureStationId, arrivalStationId));

        return new PageImpl<>(
                trainSchedules,
                trainSchedulePage.getPageable(),
                trainSchedulePage.getTotalElements()
        );
    }

    private void validateRequestGetAllSchedule(String departureStationId, String arrivalStationId,
                                               LocalDateTime departureTime,
                                               int pageNumber, int pageSize) {
        if (departureStationId.equals(arrivalStationId) || departureTime.isBefore(LocalDateTime.now())) {
            throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_INVALID_PARAM);
        }
        if (pageNumber < 1) {
            throw new BusinessException(TrainExceptionCode.TRAIN_SCHEDULE_INVALID_PARAM);
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
