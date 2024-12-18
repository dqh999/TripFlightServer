package graph.railgo.application.train.service;

import graph.railgo.application.train.dataTransferObject.request.AddTrainScheduleRequest;
import graph.railgo.application.train.dataTransferObject.response.TrainScheduleResponse;
import graph.railgo.application.utils.PageResponse;

public interface ITrainScheduleUseCase {
    TrainScheduleResponse addTrainSchedule(AddTrainScheduleRequest request);
    PageResponse<TrainScheduleResponse> getAllSchedules(String departureStationId,String arrivalStationId,
                                                        String departureTime,String arrivalTime,
                                                        int pageNo,int pageSize, String sortBy);
}
