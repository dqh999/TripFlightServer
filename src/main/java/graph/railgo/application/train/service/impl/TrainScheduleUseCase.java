package graph.railgo.application.train.service.impl;

import graph.railgo.application.train.dataTransferObject.request.AddTrainScheduleRequest;
import graph.railgo.application.train.dataTransferObject.response.TrainScheduleResponse;
import graph.railgo.application.train.service.ITrainScheduleUseCase;
import graph.railgo.application.utils.PageResponse;
import graph.railgo.domain.train.service.ITrainScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainScheduleUseCase implements ITrainScheduleUseCase {
    private final ITrainScheduleService trainScheduleService;

    @Autowired
    public TrainScheduleUseCase(ITrainScheduleService trainScheduleService) {
        this.trainScheduleService = trainScheduleService;
    }

    @Override
    public TrainScheduleResponse addTrainSchedule(AddTrainScheduleRequest request) {
        return null;
    }

    @Override
    public PageResponse<TrainScheduleResponse> getAllSchedules(String departureStationId, String arrivalStationId,
                                                               String departureTime, String arrivalTime,
                                                               int pageNo, int pageSize, String sortBy) {

        return null;
    }
}
