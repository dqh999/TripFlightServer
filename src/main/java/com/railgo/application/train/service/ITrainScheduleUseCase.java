package com.railgo.application.train.service;

import com.railgo.application.train.dataTransferObject.request.AddTrainScheduleRequest;
import com.railgo.application.train.dataTransferObject.response.TrainScheduleResponse;
import com.railgo.application.utils.PageResponse;
import com.railgo.infrastructure.security.UserDetail;

import java.time.LocalDateTime;

public interface ITrainScheduleUseCase {
    TrainScheduleResponse addTrainSchedule(UserDetail userRequest,AddTrainScheduleRequest request);
    PageResponse<TrainScheduleResponse> getAllSchedules(String departureStationId,String arrivalStationId,
                                                        LocalDateTime departureTime,
                                                        int pageNo,int pageSize, String sortBy);
}
