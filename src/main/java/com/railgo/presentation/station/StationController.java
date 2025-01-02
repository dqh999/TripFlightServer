package com.railgo.presentation.station;

import com.railgo.application.station.dataTransferObject.request.AddStationRequest;
import com.railgo.application.station.dataTransferObject.request.AddStationRouteRequest;
import com.railgo.application.station.service.IStationUseCase;
import com.railgo.infrastructure.exception.ApiResponse;
import com.railgo.infrastructure.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/station")
public class StationController {
    private final IStationUseCase stationUseCase;

    @Autowired
    public StationController(IStationUseCase stationUseCase) {
        this.stationUseCase = stationUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> handelAddStation(@AuthenticationPrincipal UserDetail userRequest,
                                              @RequestBody AddStationRequest request) {
        var result = stationUseCase.addStation(userRequest, request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }

    @PostMapping("/route")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> handleAddRouteStation(@AuthenticationPrincipal UserDetail userRequest,
                                                   @RequestBody List<AddStationRouteRequest> requests) {
        var result = requests.stream().map(request -> {
            return stationUseCase.addRoute(userRequest, request);
        }).collect(Collectors.toList());
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
}
