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
import com.railgo.domain.ticket.service.ITicketPricingService;
import com.railgo.domain.train.model.Train;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.model.schedule.TrainScheduleRoute;
import com.railgo.domain.train.service.ITrainScheduleService;
import com.railgo.domain.train.service.ITrainService;
import com.railgo.domain.utils.valueObject.Money;
import com.railgo.infrastructure.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainScheduleUseCase implements ITrainScheduleUseCase {
    private final ITrainService trainService;
    private final ITrainScheduleService trainScheduleService;
    private final IStationService stationService;
    private final IStationRouteService stationRouteService;
    private final ITicketPricingService ticketPricingService;
    private final TrainScheduleMapper trainScheduleMapper;

    @Autowired
    public TrainScheduleUseCase(ITrainService trainService,
                                ITrainScheduleService trainScheduleService,
                                IStationService stationService,
                                IStationRouteService stationRouteService,
                                ITicketPricingService ticketPricingService,
                                TrainScheduleMapper trainScheduleMapper) {
        this.trainService = trainService;
        this.trainScheduleService = trainScheduleService;
        this.stationService = stationService;
        this.stationRouteService = stationRouteService;
        this.ticketPricingService = ticketPricingService;
        this.trainScheduleMapper = trainScheduleMapper;
    }

    @Override
    public TrainScheduleResponse addTrainSchedule(UserDetail userRequest,
                                                  AddTrainScheduleRequest request) {
        Train train = trainService.getTrainById(request.getTrainId());

        String departureStationId = request.getDepartureStationId();
        String arrivalStationId = request.getArrivalStationId();

        Station departureStation = stationService.getStation(departureStationId);
        Station arrivalStation = stationService.getStation(arrivalStationId);

        List<StationRoute> routes = stationRouteService.getRoutesBetweenStations(departureStation, arrivalStation, train);

        LocalDateTime departureTime = request.getDepartureTime();

        List<TrainScheduleRoute> trainScheduleRoutes = buildTrainScheduleRoutes(routes, train, departureTime);

        LocalDateTime arrivalTime = calculateArrivalTime(routes.getLast(), train, trainScheduleRoutes.getLast());

        Money totalPrice = calculateTotalPrice(train, trainScheduleRoutes, routes.getLast());

        String creatorId = userRequest.getId();

        TrainSchedule trainSchedule = initializeTrainSchedule(creatorId, train, departureStationId, arrivalStationId, departureTime, arrivalTime, totalPrice, trainScheduleRoutes);
        trainSchedule.setRoutes(trainScheduleRoutes);
        TrainSchedule newTrainSchedule = trainScheduleService.addSchedule(trainSchedule);

        return trainScheduleMapper.toDTO(newTrainSchedule);
    }

    private List<TrainScheduleRoute> buildTrainScheduleRoutes(List<StationRoute> routes,
                                                              Train train,
                                                              LocalDateTime departureTime) {
        List<TrainScheduleRoute> scheduleRoutes = new ArrayList<>();
        LocalDateTime currentDepartureTime = departureTime;

        for (int i = 0; i < routes.size() - 1; i++) {
            StationRoute route = routes.get(i);

            Station stationA = route.getStationA();
            Station stationB = route.getStationB();

            Double travelTime = stationRouteService.getTravelTime(stationA, stationB, train);

            LocalDateTime nextArrivalTime = currentDepartureTime.plusMinutes((long) (travelTime * 60));

            Money ticketPrice = ticketPricingService.calculateTicketPrice(train, route);

            TrainScheduleRoute trainScheduleRoute = new TrainScheduleRoute(stationB.getId(), nextArrivalTime, ticketPrice.getValue(), ticketPrice.getCurrency());
            scheduleRoutes.add(trainScheduleRoute);

            currentDepartureTime = nextArrivalTime;
        }
        return scheduleRoutes;
    }

    private LocalDateTime calculateArrivalTime(StationRoute lastStationRoute,
                                               Train train,
                                               TrainScheduleRoute lastTrainScheduleRoute) {
        Double travelTimeLast = stationRouteService.getTravelTime(lastStationRoute.getStationA(), lastStationRoute.getStationB(), train);
        return lastTrainScheduleRoute.getArrivalTime().plusMinutes((long) (travelTimeLast * 60));
    }

    private Money calculateTotalPrice(Train train, List<TrainScheduleRoute> trainScheduleRoutes, StationRoute lastRoute) {
        Money lastTicketPrice = ticketPricingService.calculateTicketPrice(train, lastRoute);

        Money totalPrice = new Money(BigDecimal.ZERO, lastTicketPrice.getCurrency());
        totalPrice = totalPrice.add(lastTicketPrice);

        for (TrainScheduleRoute route : trainScheduleRoutes) {
            totalPrice = totalPrice.add(new Money(route.getTicketPrice(), route.getCurrency()));
        }

        return totalPrice;
    }

    private TrainSchedule initializeTrainSchedule(String creatorId, Train train,
                                                  String departureStationId, String arrivalStationId,
                                                  LocalDateTime departureTime, LocalDateTime arrivalTime,
                                                  Money totalPrice,
                                                  List<TrainScheduleRoute> trainScheduleRoutes) {
        TrainSchedule trainSchedule = new TrainSchedule();

        trainSchedule.setCreatorId(creatorId);
        trainSchedule.setTrain(train);
        trainSchedule.setDepartureStationId(departureStationId);
        trainSchedule.setArrivalStationId(arrivalStationId);
        trainSchedule.setDepartureTime(departureTime);
        trainSchedule.setArrivalTime(arrivalTime);
        trainSchedule.setTicketPrice(totalPrice.getValue(), totalPrice.getCurrency());
        trainSchedule.setTotalStops(trainScheduleRoutes.size());

        trainScheduleRoutes.forEach(route -> route.setScheduleId(trainSchedule.getId()));
        trainSchedule.setRoutes(trainScheduleRoutes);

        return trainSchedule;
    }

    @Override
    public PageResponse<TrainScheduleResponse> getAllSchedules(String departureStationId, String arrivalStationId,
                                                               LocalDateTime departureTime,
                                                               int pageNo, int pageSize, String sortBy) {
        Page<TrainSchedule> trainSchedulePage = trainScheduleService.getAllSchedules(departureStationId, arrivalStationId, departureTime, pageNo, pageSize);

        List<TrainScheduleResponse> trainScheduleResponses = new ArrayList<>();
        buildTrainScheduleResponse(trainSchedulePage.getContent(), departureStationId, arrivalStationId, trainScheduleResponses);

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
                                            List<TrainScheduleResponse> trainScheduleResponses) {
        trainSchedules.forEach(trainSchedule -> {

            TrainScheduleResponse trainScheduleResponse = trainScheduleMapper.toDTO(trainSchedule);
            trainScheduleResponse.setDepartureStationId(departureStationId);
            trainScheduleResponse.setArrivalStationId(arrivalStationId);
            trainScheduleResponse.setRoutes(new ArrayList<>());

            if (isDirectRoute(trainSchedule, departureStationId, arrivalStationId)) {
                trainScheduleResponse.setTicketPrice(trainSchedule.getTicketPrice());
                trainScheduleResponse.setCurrency(trainSchedule.getCurrency());

                trainScheduleResponses.add(trainScheduleResponse);
                return;
            }

            TrainScheduleResponse responseForIndirectRoute = processIndirectRoute(trainSchedule, departureStationId, arrivalStationId, trainScheduleResponse);
            trainScheduleResponses.add(responseForIndirectRoute);
        });
    }

    private boolean isDirectRoute(TrainSchedule trainSchedule, String departureStationId, String arrivalStationId) {
        return trainSchedule.getDepartureStationId().equals(departureStationId) &&
                trainSchedule.getArrivalStationId().equals(arrivalStationId);
    }

    private TrainScheduleResponse processIndirectRoute(TrainSchedule trainSchedule,
                                                       String departureStationId, String arrivalStationId,
                                                       TrainScheduleResponse trainScheduleResponse) {
        List<TrainScheduleRoute> trainScheduleRoutes = trainSchedule.getRoutes();

        int trainScheduleRoutesSize = trainScheduleRoutes.size();

        if (trainSchedule.getDepartureStationId().equals(departureStationId)) {
            Money totalPrice = new Money(BigDecimal.ZERO, trainSchedule.getCurrency());
            for (int i = 0; i < trainScheduleRoutesSize; i++) {
                TrainScheduleRoute trainScheduleRoute = trainScheduleRoutes.get(i);
                totalPrice = totalPrice.add(new Money(trainScheduleRoute.getTicketPrice(), trainSchedule.getCurrency()));

                if (trainScheduleRoute.getStationId().equals(arrivalStationId)) {
                    trainScheduleResponse.setTicketPrice(totalPrice.getValue());
                    trainScheduleResponse.setCurrency(trainSchedule.getCurrency());

                    trainScheduleResponse.setArrivalTime(trainScheduleRoute.getArrivalTime());
                    trainScheduleResponse.setTotalStops(i);
                    return trainScheduleResponse;
                }

            }
        }

        boolean hasDepartureStation = false;
        int totalStops = 0;

        Money totalPrice = new Money(BigDecimal.ZERO, trainSchedule.getCurrency());

        for (int i = 0; i < trainScheduleRoutesSize; i++) {
            TrainScheduleRoute trainScheduleRoute = trainScheduleRoutes.get(i);

            if (hasDepartureStation) {
                totalPrice = totalPrice.add(new Money(trainScheduleRoute.getTicketPrice(), trainSchedule.getCurrency()));
                totalStops++;
            }

            if (trainScheduleRoute.getStationId().equals(departureStationId)) {
                trainScheduleResponse.setDepartureTime(trainScheduleRoute.getArrivalTime());

                hasDepartureStation = true;

                if (trainSchedule.getArrivalStationId().equals(arrivalStationId)) {

                    trainScheduleResponse.setTotalStops(trainSchedule.getTotalStops() - i - 1);
                    return trainScheduleResponse;
                }

            } else if (trainScheduleRoute.getStationId().equals(arrivalStationId) && hasDepartureStation) {
                trainScheduleResponse.setTotalStops(totalStops - 1);
                trainScheduleResponse.setArrivalTime(trainScheduleRoute.getArrivalTime());
                trainScheduleResponse.setTicketPrice(totalPrice.getValue());
                trainScheduleResponse.setCurrency(trainSchedule.getCurrency());
                return trainScheduleResponse;
            }

        }
        return trainScheduleResponse;
    }

}
