package com.railgo.application.train.service.impl;

import com.railgo.application.station.dataTransferObject.response.StationResponse;
import com.railgo.application.station.service.IStationUseCase;
import com.railgo.application.train.dataTransferObject.request.AddTrainScheduleRequest;
import com.railgo.application.train.dataTransferObject.response.TrainScheduleResponse;
import com.railgo.application.train.mapper.TrainScheduleMapper;
import com.railgo.application.train.service.ITrainScheduleUseCase;
import com.railgo.application.utils.PageResponse;
import com.railgo.domain.station.model.Station;
import com.railgo.domain.station.model.StationRoute;
import com.railgo.domain.station.service.IStationRouteService;
import com.railgo.domain.station.service.IStationService;
import com.railgo.domain.ticket.service.ITicketPricingService;
import com.railgo.domain.train.model.Train;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.model.schedule.TrainScheduleStop;
import com.railgo.domain.train.service.ITrainScheduleService;
import com.railgo.domain.train.service.ITrainScheduleStopService;
import com.railgo.domain.train.service.ITrainService;
import com.railgo.domain.utils.valueObject.Money;
import com.railgo.infrastructure.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class TrainScheduleUseCase implements ITrainScheduleUseCase {
//    private static final Logger logger = LoggerFactory.getLogger(TrainScheduleUseCase.class);

    private final ITrainService trainService;
    private final ITrainScheduleService trainScheduleService;
    private final ITrainScheduleStopService trainScheduleStopService;
    private final IStationUseCase stationUseCase;
    private final IStationService stationService;
    private final IStationRouteService stationRouteService;
    private final ITicketPricingService ticketPricingService;
    private final TrainScheduleMapper trainScheduleMapper;

    @Autowired
    public TrainScheduleUseCase(ITrainService trainService,
                                ITrainScheduleService trainScheduleService,
                                ITrainScheduleStopService trainScheduleStopService,
                                IStationUseCase stationUseCase,
                                IStationService stationService,
                                IStationRouteService stationRouteService,
                                ITicketPricingService ticketPricingService,
                                TrainScheduleMapper trainScheduleMapper) {
        this.trainService = trainService;
        this.trainScheduleService = trainScheduleService;
        this.trainScheduleStopService = trainScheduleStopService;
        this.stationUseCase =  stationUseCase;
        this.stationService = stationService;
        this.stationRouteService = stationRouteService;
        this.ticketPricingService = ticketPricingService;
        this.trainScheduleMapper = trainScheduleMapper;
    }

    @Override
    public TrainScheduleResponse addTrainSchedule(UserDetail userRequest,
                                                  AddTrainScheduleRequest request) {
        Train train = trainService.getTrainById(request.getTrainId());

        Station departureStation = stationService.getStation(request.getDepartureStationId());
        Station arrivalStation = stationService.getStation(request.getArrivalStationId());

        List<StationRoute> routes = stationRouteService.getRoutesBetweenStations(departureStation, arrivalStation, train);
        List<TrainScheduleStop> trainScheduleStops = buildTrainScheduleStops(routes, train, request.getDepartureTime());

        TrainSchedule trainSchedule = buildTrainSchedule(train, trainScheduleStops);
        TrainSchedule newTrainSchedule = trainScheduleService.addSchedule(trainSchedule);

        TrainScheduleResponse response = buildTrainScheduleResponse(newTrainSchedule);

        Money standardTicketPrice = ticketPricingService.calculateStandardTicketPrice(trainSchedule);

        response.setTicketPrice(standardTicketPrice);

        return response;
    }

    private List<TrainScheduleStop> buildTrainScheduleStops(List<StationRoute> routes,
                                                            Train train,
                                                            LocalDateTime departureTime) {
        List<TrainScheduleStop> scheduleRoutes = new ArrayList<>();
        LocalDateTime currentDepartureTime = departureTime;

        for (int i = 0; i < routes.size(); i++) {
            StationRoute route = routes.get(i);
            Station stationA = route.getStationA();
            Station stationB = route.getStationB();

            Double travelTime = stationRouteService.getTravelTime(stationA, stationB, train);
            LocalDateTime nextArrivalTime = currentDepartureTime.plusMinutes((long) (travelTime * 60));

            Money ticketPrice = ticketPricingService.calculateBaseFareForRoute(train, route);

            TrainScheduleStop trainScheduleStop = new TrainScheduleStop(i, stationA.getId(), departureTime, stationB.getId(), nextArrivalTime, train.getTotalSeats(), ticketPrice);
            scheduleRoutes.add(trainScheduleStop);

            currentDepartureTime = nextArrivalTime.plusMinutes(15);
        }
        return scheduleRoutes;
    }

    private TrainSchedule buildTrainSchedule(Train train,
                                             List<TrainScheduleStop> trainScheduleStops) {
        TrainSchedule trainSchedule = new TrainSchedule();
        trainSchedule.setTrain(train);
        trainSchedule.setTotalStops(trainScheduleStops.size());
        trainScheduleStops.forEach(route -> route.setScheduleId(trainSchedule.getId()));
        trainSchedule.setStops(trainScheduleStops);
        return trainSchedule;
    }


    private TrainScheduleResponse buildTrainScheduleResponse(TrainSchedule trainSchedule) {
        TrainScheduleResponse trainScheduleResponse = trainScheduleMapper.toDTO(trainSchedule);

        List<TrainScheduleStop> trainScheduleStops = trainSchedule.getStops();

        TrainScheduleStop trainScheduleStopFirst = trainScheduleStops.getFirst();
        StationResponse departureStation = stationUseCase.getStation(trainScheduleStopFirst.getStationId());
        trainScheduleResponse.setDepartureStation(departureStation);
        trainScheduleResponse.setDepartureTime(trainScheduleStopFirst.getDepartureTime());

        TrainScheduleStop trainScheduleStopLast = trainScheduleStops.getLast();
        StationResponse arrivalStation = stationUseCase.getStation(trainScheduleStopLast.getNextStationId());
        trainScheduleResponse.setArrivalStation(arrivalStation);
        trainScheduleResponse.setArrivalTime(trainScheduleStopLast.getArrivalTime());

        return trainScheduleResponse;
    }

    @Override
    public PageResponse<TrainScheduleResponse> getAllSchedules(String departureStationId, String arrivalStationId,
                                                               LocalDateTime departureTime,
                                                               int childSeats, int adultSeats, int seniorSeats,
                                                               int pageNo, int pageSize, String sortBy) {
        Page<TrainSchedule> trainSchedulePage = trainScheduleService.getAllSchedules(departureStationId, arrivalStationId, departureTime, pageNo, pageSize);

        List<TrainScheduleResponse> trainScheduleResponses = trainSchedulePage.stream()
                .filter(trainSchedule -> {
                    int totalAvailableSeat = trainScheduleStopService.calculateAvailableSeats(trainSchedule.getStops());
                    int totalRequestSeat = childSeats + adultSeats + seniorSeats;
                    return totalAvailableSeat >= totalRequestSeat;
                })
                .map(trainSchedule -> {
                    TrainScheduleResponse response = buildTrainScheduleResponse(trainSchedule);
                    Money standardTicketPrice = ticketPricingService.calculateStandardTicketPrice(trainSchedule);
                    Money ticketPrice = ticketPricingService.calculateTicketPriceForPassengers(standardTicketPrice,childSeats,adultSeats,seniorSeats);
                    response.setTicketPrice(ticketPrice);
                    return response;
                })
                .collect(Collectors.toList());

        return new PageResponse<>(
                (int) trainSchedulePage.getTotalElements(),
                trainSchedulePage.getTotalPages(),
                pageNo,
                trainScheduleResponses.size(),
                trainScheduleResponses,
                trainSchedulePage.hasNext(),
                trainSchedulePage.hasPrevious()
        );
    }
}
