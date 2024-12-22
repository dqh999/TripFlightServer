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

        TrainSchedule trainSchedule = buildTrainSchedule(train, departureStation, arrivalStation, departureTime, arrivalTime, totalPrice, trainScheduleRoutes);
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

            TrainScheduleRoute trainScheduleRoute = new TrainScheduleRoute(stationB.getId(), nextArrivalTime, new Money(ticketPrice.getValue(), ticketPrice.getCurrency()));
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
            totalPrice = totalPrice.add(route.getTicketPrice());
        }

        return totalPrice;
    }

    private TrainSchedule buildTrainSchedule(Train train, Station departureStation, Station arrivalStation,
                                             LocalDateTime departureTime, LocalDateTime arrivalTime, Money totalPrice,
                                             List<TrainScheduleRoute> trainScheduleRoutes) {
        TrainSchedule trainSchedule = new TrainSchedule(train, departureStation.getId(), arrivalStation.getId(), departureTime, arrivalTime, totalPrice);
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
            trainScheduleResponse.setRoutes(new ArrayList<>());

            if (isDirectRoute(trainSchedule, departureStationId, arrivalStationId)) {
                trainScheduleResponses.add(trainScheduleResponse);
                return;
            }

            List<TrainScheduleRoute> trainScheduleRoutes = trainSchedule.getRoutes();

            String currency = trainSchedule.getTicketPrice().getCurrency();
            TrainScheduleRoute firstRoute = new TrainScheduleRoute(trainSchedule.getDepartureStationId(), trainSchedule.getDepartureTime(), new Money(BigDecimal.ZERO,currency));

            Money priceTicketRouteLast = calculateTicketPriceForLastRoute(trainScheduleRoutes, trainSchedule.getTicketPrice());
            TrainScheduleRoute lastRoute = new TrainScheduleRoute(trainSchedule.getArrivalStationId(), trainSchedule.getArrivalTime(), new Money(priceTicketRouteLast.getValue(), currency));

            List<TrainScheduleRoute> newTrainScheduleRoutes = buildNewRoutesList(firstRoute, trainScheduleRoutes, lastRoute);

            calculateTrainScheduleDetails(newTrainScheduleRoutes, departureStationId, arrivalStationId, trainScheduleResponse);
            trainScheduleResponses.add(trainScheduleResponse);
        });
    }

    private boolean isDirectRoute(TrainSchedule trainSchedule, String departureStationId, String arrivalStationId) {
        return trainSchedule.getDepartureStationId().equals(departureStationId) && trainSchedule.getArrivalStationId().equals(arrivalStationId);
    }

    private Money calculateTicketPriceForLastRoute(List<TrainScheduleRoute> routes, Money totalPrice) {
        Money ticketPrice = new Money(BigDecimal.ZERO, totalPrice.getCurrency());
        for (int i = 0; i < routes.size() - 1; i++) {
            ticketPrice = ticketPrice.add(routes.get(i).getTicketPrice());
        }
        return totalPrice.subtract(ticketPrice);
    }

    private List<TrainScheduleRoute> buildNewRoutesList(TrainScheduleRoute firstRoute, List<TrainScheduleRoute> trainScheduleRoutes, TrainScheduleRoute lastRoute) {
        List<TrainScheduleRoute> newRoutes = new ArrayList<>();
        newRoutes.add(firstRoute);
        newRoutes.addAll(trainScheduleRoutes);
        newRoutes.add(lastRoute);
        return newRoutes;
    }

    private void calculateTrainScheduleDetails(List<TrainScheduleRoute> trainScheduleRoutes,
                                               String departureStationId, String arrivalStationId,
                                               TrainScheduleResponse trainScheduleResponse) {
        String currency = trainScheduleRoutes.getFirst().getTicketPrice().getCurrency();
        Money totalPrice = new Money(BigDecimal.ZERO, currency);
        boolean isInRange = false;

        for (TrainScheduleRoute trainScheduleRoute : trainScheduleRoutes) {
            if (isInRange) {
                totalPrice = totalPrice.add(trainScheduleRoute.getTicketPrice());
            }
            if (trainScheduleRoute.getStationId().equals(departureStationId)) {
                isInRange = true;
                trainScheduleResponse.setDepartureStationId(trainScheduleRoute.getStationId());
                trainScheduleResponse.setDepartureTime(trainScheduleRoute.getArrivalTime());
            }
            if (trainScheduleRoute.getStationId().equals(arrivalStationId) && isInRange) {
                trainScheduleResponse.setArrivalStationId(trainScheduleRoute.getStationId());
                trainScheduleResponse.setArrivalTime(trainScheduleRoute.getArrivalTime());

                trainScheduleResponse.setTicketPrice(totalPrice);
                return;
            }
        }
    }
}
