package com.railgo.domain.ticket.service.impl;

import com.railgo.domain.station.model.StationRoute;
import com.railgo.domain.ticket.service.ITicketPricingService;
import com.railgo.domain.train.model.Train;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.train.model.schedule.TrainScheduleStop;
import com.railgo.domain.utils.type.Currency;
import com.railgo.domain.utils.valueObject.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ITicketPricingServiceImpl implements ITicketPricingService {
    @Override
    public Money calculateScheduleTicketPrice(TrainSchedule trainSchedule,
                                              String startStationId, String endStationId) {
        String currency = trainSchedule.getTicketPrice().getCurrency();
        Money totalPrice = new Money(BigDecimal.ZERO, currency);
        List<TrainScheduleStop> trainScheduleStops = trainSchedule.getStops();
        boolean hasStartStation = false;
        for (TrainScheduleStop trainScheduleStop : trainScheduleStops) {
            if (hasStartStation) {
                totalPrice = totalPrice.add(trainScheduleStop.getTicketPrice());
            }
            if (trainScheduleStop.getStationId().equals(startStationId)) {
                hasStartStation = true;
                continue;
            }
            if (trainScheduleStop.getStationId().equals(endStationId)) {
                return totalPrice;
            }
        }
        return totalPrice;
    }

    @Override
    public Money calculateTicketPrice(Train train, StationRoute stationRoute) {
        BigDecimal baseFarePerKm = BigDecimal.valueOf(500).multiply(train.getRatePerKm());

        Double distance = stationRoute.getDistanceKm();
        BigDecimal ticketPrice = baseFarePerKm.multiply(BigDecimal.valueOf(distance));

        return new Money(ticketPrice, Currency.VND.getValue());
    }
}
