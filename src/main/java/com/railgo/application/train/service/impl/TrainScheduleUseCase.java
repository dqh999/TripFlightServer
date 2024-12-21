package com.railgo.application.train.service.impl;

import com.railgo.application.train.dataTransferObject.request.AddTrainScheduleRequest;
import com.railgo.application.train.dataTransferObject.response.TrainScheduleResponse;
import com.railgo.application.train.mapper.TrainScheduleMapper;
import com.railgo.application.train.service.ITrainScheduleUseCase;
import com.railgo.application.utils.PageResponse;
import com.railgo.domain.station.model.Station;
import com.railgo.domain.station.model.StationRoute;
import com.railgo.domain.station.service.IStationRouteService;
import com.railgo.domain.station.service.IStationService;
import com.railgo.domain.train.model.Train;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.model.schedule.TrainScheduleRoute;
import com.railgo.domain.train.service.ITrainScheduleService;
import com.railgo.domain.train.service.ITrainService;
import com.railgo.infrastructure.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainScheduleUseCase implements ITrainScheduleUseCase {
    private final ITrainService trainService;
    private final ITrainScheduleService trainScheduleService;
    private final IStationService stationService;
    private final IStationRouteService stationRouteService;
    private final TrainScheduleMapper trainScheduleMapper;

    @Autowired
    public TrainScheduleUseCase(ITrainService trainService,
                                ITrainScheduleService trainScheduleService,
                                IStationService stationService,
                                IStationRouteService stationRouteService,
                                TrainScheduleMapper trainScheduleMapper) {
        this.trainService = trainService;
        this.trainScheduleService = trainScheduleService;
        this.stationService = stationService;
        this.stationRouteService = stationRouteService;
        this.trainScheduleMapper = trainScheduleMapper;
    }

    @Override
    public TrainScheduleResponse addTrainSchedule(UserDetail userRequest,
                                                  AddTrainScheduleRequest request) {
        String creatorId = userRequest.getId();

        Train train = trainService.getTrainById(request.getTrainId());

        String departureStationId = request.getDepartureStationId();
        String arrivalStationId = request.getArrivalStationId();

        Station departureStation = stationService.getStation(departureStationId);
        Station arrivalStation = stationService.getStation(arrivalStationId);

        List<StationRoute> routes = stationRouteService.getRoutesBetweenStations(departureStation, arrivalStation, train);

        List<TrainScheduleRoute> trainScheduleRoutes = new ArrayList<>();

        LocalDateTime departureTime = request.getDepartureTime();

        int index = 0;
        buildTrainScheduleRoutes(trainScheduleRoutes,train,routes,departureTime,index);

        StationRoute lastRoute = routes.get(index);
        Double travelTimeLast = stationRouteService.getTravelTime(lastRoute.getStationA(),lastRoute.getStationB(),train);
        LocalDateTime arrivalTime = trainScheduleRoutes.getLast().getArrivalTime().plusMinutes((long)(travelTimeLast * 60));

        TrainSchedule trainSchedule = new TrainSchedule(creatorId,train,departureStationId,arrivalStationId,departureTime,arrivalTime);

        Integer totalStops = routes.size();
        trainScheduleRoutes.forEach(route -> route.setScheduleId(trainSchedule.getId()));

        trainSchedule.setTotalStops(totalStops);
        trainSchedule.setRoutes(trainScheduleRoutes);

        TrainSchedule newTrainSchedule = trainScheduleService.addSchedule(trainSchedule);

        return trainScheduleMapper.toDTO(newTrainSchedule);
    }

    private List<TrainScheduleRoute> buildTrainScheduleRoutes(List<TrainScheduleRoute> result,
                                                              Train train,
                                                              List<StationRoute> routes,
                                                              LocalDateTime departureTime,
                                                              int index){
        if (routes.isEmpty() || index >= routes.size()) return null;

        StationRoute route = routes.get(index);

        Station stationA = route.getStationA();
        Station stationB = route.getStationB();

        Double travelTime = stationRouteService.getTravelTime(stationA,stationB,train);
        LocalDateTime nextArrivalTime = departureTime.plusMinutes((long)(travelTime * 60));

        TrainScheduleRoute trainScheduleRoute = new TrainScheduleRoute(stationB.getId(),nextArrivalTime);

        result.add(trainScheduleRoute);

        index = index + 1;
        return buildTrainScheduleRoutes(result,train, routes,nextArrivalTime, index);
    }

    @Override
    public PageResponse<TrainScheduleResponse> getAllSchedules(String departureStationId, String arrivalStationId,
                                                               LocalDateTime departureTime,
                                                               int pageNo, int pageSize, String sortBy) {
        Page<TrainSchedule> trainSchedulePage = trainScheduleService.getAllSchedules(departureStationId, arrivalStationId, departureTime, pageNo, pageSize);

        List<TrainScheduleResponse> trainScheduleResponses = new ArrayList<>();
        buildTrainScheduleResponse(trainSchedulePage.getContent(),departureStationId,arrivalStationId,trainScheduleResponses);

        return new PageResponse<>(
                (int) trainSchedulePage.getTotalElements(),
                trainSchedulePage.getTotalPages(),
                pageNo,
                trainSchedulePage.getSize(),
                trainScheduleResponses,
                trainSchedulePage.hasNext(),
                trainSchedulePage.hasPrevious()
        );
    }

    private void buildTrainScheduleResponse(List<TrainSchedule> trainSchedules,
                                            String departureStationId, String arrivalStationId,
                                            List<TrainScheduleResponse> trainScheduleResponses){
        trainSchedules.forEach(trainSchedule-> {

            List<TrainScheduleRoute> trainScheduleRoutes = trainSchedule.getRoutes();
            TrainScheduleResponse trainScheduleResponse = trainScheduleMapper.toDTO(trainSchedule);
            trainScheduleResponse.setDepartureStationId(departureStationId);
            trainScheduleResponse.setArrivalStationId(arrivalStationId);

            if (trainSchedule.getDepartureStationId().equals(departureStationId)) {
                if (trainSchedule.getArrivalStationId().equals(arrivalStationId)) {
                    trainScheduleResponses.add(trainScheduleResponse);
                    return;
                }

                for (TrainScheduleRoute trainScheduleRoute : trainScheduleRoutes) {
                    if (trainScheduleRoute.getStationId().equals(arrivalStationId)) {
                        trainScheduleResponse.setArrivalTime(trainScheduleRoute.getArrivalTime());
                        trainScheduleResponses.add(trainScheduleResponse);
                        return;
                    }
                }
            }

            boolean hasDepartureStation = false;
            for (TrainScheduleRoute trainScheduleRoute : trainScheduleRoutes) {
                if (trainScheduleRoute.getStationId().equals(departureStationId)) {
                    trainScheduleResponse.setDepartureTime(trainScheduleRoute.getArrivalTime());
                    hasDepartureStation = true;
                } else if (trainScheduleRoute.getStationId().equals(arrivalStationId) && hasDepartureStation) {
                    trainScheduleResponse.setArrivalTime(trainScheduleRoute.getArrivalTime());
                    trainScheduleResponses.add(trainScheduleResponse);
                    return;
                }
            }
            trainScheduleResponses.add(trainScheduleResponse);
        });
    }

}
