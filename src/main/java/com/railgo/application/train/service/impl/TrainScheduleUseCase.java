package com.railgo.application.train.service.impl;

import com.railgo.application.train.dataTransferObject.request.AddTrainScheduleRequest;
import com.railgo.application.train.dataTransferObject.response.TrainScheduleResponse;
import com.railgo.application.train.dataTransferObject.response.TrainScheduleStopResponse;
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
import com.railgo.domain.utils.type.Currency;
import com.railgo.domain.utils.valueObject.Money;
import com.railgo.infrastructure.exception.ApplicationException;
import com.railgo.infrastructure.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainScheduleUseCase implements ITrainScheduleUseCase {
    private final ITrainService trainService;
    private final ITrainScheduleService trainScheduleService;
    private final ITrainScheduleStopService trainScheduleStopService;
    private final IStationService stationService;
    private final IStationRouteService stationRouteService;
    private final ITicketPricingService ticketPricingService;
    private final TrainScheduleMapper trainScheduleMapper;

    @Autowired
    public TrainScheduleUseCase(ITrainService trainService,
                                ITrainScheduleService trainScheduleService,
                                ITrainScheduleStopService trainScheduleStopService,
                                IStationService stationService,
                                IStationRouteService stationRouteService,
                                ITicketPricingService ticketPricingService,
                                TrainScheduleMapper trainScheduleMapper) {
        this.trainService = trainService;
        this.trainScheduleService = trainScheduleService;
        this.trainScheduleStopService = trainScheduleStopService;
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

        Money ticketPrice = calculateTicketPrice(trainScheduleStops);
        TrainSchedule trainSchedule = buildTrainSchedule(train, ticketPrice, trainScheduleStops);
        TrainSchedule newTrainSchedule = trainScheduleService.addSchedule(trainSchedule);

        return buildTrainScheduleResponse(newTrainSchedule, departureStation.getId(), arrivalStation.getId());
    }

    private List<TrainScheduleStop> buildTrainScheduleStops(List<StationRoute> routes,
                                                            Train train,
                                                            LocalDateTime departureTime) {
        List<TrainScheduleStop> scheduleRoutes = new ArrayList<>();

        String currency = Currency.VND.getValue();

        TrainScheduleStop trainScheduleStopFirst = createTrainScheduleStop(train,routes.getFirst().getStationA().getId(), departureTime, BigDecimal.ZERO, currency);
        scheduleRoutes.add(trainScheduleStopFirst);

        LocalDateTime currentDepartureTime = departureTime;

        for (StationRoute route : routes) {
            Station stationB = route.getStationB();

            Double travelTime = stationRouteService.getTravelTime(route.getStationA(), stationB, train);

            LocalDateTime nextArrivalTime = currentDepartureTime.plusMinutes((long) (travelTime * 60));

            Money ticketPrice = ticketPricingService.calculateTicketPrice(train, route);

            TrainScheduleStop trainScheduleStopB = createTrainScheduleStop(train,stationB.getId(), nextArrivalTime, ticketPrice.getValue(), currency);
            scheduleRoutes.add(trainScheduleStopB);

            currentDepartureTime = nextArrivalTime;
        }
        return scheduleRoutes;
    }

    private TrainScheduleStop createTrainScheduleStop(Train train,String stationId, LocalDateTime arrivalTime, BigDecimal ticketPrice, String currency) {
        return new TrainScheduleStop(stationId, arrivalTime,train.getTotalSeats(), new Money(ticketPrice, currency));
    }

    public Money calculateTicketPrice(List<TrainScheduleStop> trainScheduleStops) {
        Money ticketPrice = new Money(BigDecimal.ZERO, Currency.VND.getValue());
        for (TrainScheduleStop trainScheduleStop : trainScheduleStops) {
            ticketPrice = ticketPrice.add(trainScheduleStop.getTicketPrice());
        }
        return ticketPrice;
    }

    private TrainSchedule buildTrainSchedule(Train train, Money totalPrice,
                                             List<TrainScheduleStop> trainScheduleStops) {
        TrainSchedule trainSchedule = new TrainSchedule(train, totalPrice);
        trainSchedule.setTotalStops(trainScheduleStops.size());
        trainScheduleStops.forEach(route -> route.setScheduleId(trainSchedule.getId()));
        trainSchedule.setStops(trainScheduleStops);
        return trainSchedule;
    }

    @Override
    public TrainScheduleResponse buildTrainScheduleResponse(TrainSchedule trainSchedule,
                                                             String departureStationId, String arrivalStationId) {
        TrainScheduleResponse trainScheduleResponse = trainScheduleMapper.toDTO(trainSchedule);
        List<TrainScheduleStopResponse> trainScheduleStops = trainScheduleResponse.getStops();

        int totalSeats = trainScheduleStopService.calculateAvailableSeats(trainSchedule.getStops(),departureStationId,arrivalStationId);
        trainScheduleResponse.setTotalSeats(totalSeats);

        TrainScheduleStopResponse trainScheduleStopFirst = trainScheduleStops.getFirst();
        TrainScheduleStopResponse trainScheduleStopLast = trainScheduleStops.getLast();

        if (!trainScheduleStopFirst.getStationId().equals(departureStationId)
                || !trainScheduleStopLast.getStationId().equals(arrivalStationId)) {
            adjustForMismatchedStations(trainScheduleResponse, trainScheduleResponse.getStops(), departureStationId, arrivalStationId);
            return trainScheduleResponse;
        }

        trainScheduleResponse.setDepartureStationId(trainScheduleStopFirst.getStationId());
        trainScheduleResponse.setDepartureTime(trainScheduleStopFirst.getArrivalTime());
        trainScheduleResponse.setArrivalStationId(trainScheduleStopLast.getStationId());
        trainScheduleResponse.setArrivalTime(trainScheduleStopLast.getArrivalTime());

        if (trainScheduleStops.size() > 2) {
            trainScheduleStops = trainScheduleStops.subList(1, trainScheduleStops.size() - 1);
        } else {
            trainScheduleStops.clear();
        }

        trainScheduleResponse.setTotalStops(trainSchedule.getTotalStops() - 2);
        trainScheduleResponse.setStops(trainScheduleStops);

        return trainScheduleResponse;
    }

    private void adjustForMismatchedStations(TrainScheduleResponse trainScheduleResponse,
                                             List<TrainScheduleStopResponse> trainScheduleStops,
                                             String departureStationId, String arrivalStationId) {

        String currency = trainScheduleStops.getFirst().getTicketPrice().getCurrency();
        Money totalPrice = new Money(BigDecimal.ZERO, currency);

        boolean foundDeparture = false;
        int indexFoundDeparture = 0;

        for (int i = 0; i < trainScheduleStops.size(); i++) {
            TrainScheduleStopResponse trainScheduleStop = trainScheduleStops.get(i);

            if (foundDeparture) {
                totalPrice = totalPrice.add(trainScheduleStop.getTicketPrice());
            }
            if (trainScheduleStop.getStationId().equals(departureStationId)) {
                foundDeparture = true;
                indexFoundDeparture = i;

                trainScheduleResponse.setDepartureStationId(departureStationId);
                trainScheduleResponse.setDepartureTime(trainScheduleStop.getArrivalTime());

                continue;
            }
            if (trainScheduleStop.getStationId().equals(arrivalStationId) && foundDeparture) {
                trainScheduleResponse.setArrivalStationId(arrivalStationId);
                trainScheduleResponse.setArrivalTime(trainScheduleStop.getArrivalTime());
                trainScheduleResponse.setTicketPrice(totalPrice);
                trainScheduleResponse.setTotalStops(i - indexFoundDeparture - 1);
                trainScheduleResponse.setStops(trainScheduleStops.subList(indexFoundDeparture + 1, i));

                return;
            }
        }
        throw new ApplicationException("Departure or Arrival station mismatch.");
    }

    @Override
    public TrainScheduleResponse getTrainScheduleByIdAndStations(String trainScheduleId, String departureStationId, String arrivalStationId) {
        TrainSchedule trainSchedule = trainScheduleService.getScheduleByIdAndStations(trainScheduleId, departureStationId, arrivalStationId);
        return buildTrainScheduleResponse(trainSchedule, departureStationId, arrivalStationId);
    }

    @Override
    public PageResponse<TrainScheduleResponse> getAllSchedules(String departureStationId, String arrivalStationId,
                                                               LocalDateTime departureTime,
                                                               int pageNo, int pageSize, String sortBy) {
        Page<TrainSchedule> trainSchedulePage = trainScheduleService.getAllSchedules(departureStationId, arrivalStationId, departureTime, pageNo, pageSize);

        List<TrainScheduleResponse> trainScheduleResponses = trainSchedulePage.stream()
                .map(trainSchedule -> buildTrainScheduleResponse(trainSchedule, departureStationId, arrivalStationId))
                .collect(Collectors.toList());

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
}
