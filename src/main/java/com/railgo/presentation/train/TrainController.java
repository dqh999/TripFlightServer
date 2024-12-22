package com.railgo.presentation.train;

import com.railgo.application.train.dataTransferObject.request.AddTrainRequest;
import com.railgo.application.train.dataTransferObject.request.AddTrainScheduleRequest;
import com.railgo.application.train.service.ITrainScheduleUseCase;
import com.railgo.application.train.service.ITrainUseCase;
import com.railgo.infrastructure.exception.ApiResponse;
import com.railgo.infrastructure.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/train")
public class TrainController {
    private final ITrainUseCase trainUseCase;
    private final ITrainScheduleUseCase trainScheduleUseCase;

    @Autowired
    public TrainController(ITrainUseCase trainUseCase,
                           ITrainScheduleUseCase trainScheduleUseCase) {
        this.trainUseCase = trainUseCase;
        this.trainScheduleUseCase = trainScheduleUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> handelAddTrain(@AuthenticationPrincipal UserDetail userRequest,
                                            @RequestBody AddTrainRequest request) {
        var result = trainUseCase.addTrain(userRequest, request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
    @PostMapping("/schedule")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> handelAddSchedule(@AuthenticationPrincipal UserDetail userRequest,
                                               @RequestBody AddTrainScheduleRequest request) {
        var result = trainScheduleUseCase.addTrainSchedule(userRequest,request);
        return ApiResponse.build().withData(result).toEntity();
    }

    @GetMapping("/schedule")
    public ResponseEntity<?> handelGetAllSchedules(@RequestParam String departureStationId,
                                                   @RequestParam String arrivalStationId,
                                                   @RequestParam LocalDateTime departureTime,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int pageSize,
                                                   @RequestParam String sortBy) {
        var result = trainScheduleUseCase.getAllSchedules(departureStationId, arrivalStationId, departureTime, page, pageSize, sortBy);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
}
