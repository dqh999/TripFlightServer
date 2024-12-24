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
public class TicketPricingServiceImpl implements ITicketPricingService {
    private static final BigDecimal ADULT_RATE = new BigDecimal("1.0");
    private static final BigDecimal CHILD_RATE = new BigDecimal("0.5");
    private static final BigDecimal SENIOR_RATE = new BigDecimal("0.8");

    @Override
    public Money calculateStandardTicketPrice(TrainSchedule trainSchedule) {
        List<TrainScheduleStop> trainScheduleStops = trainSchedule.getStops();
        String currency = trainScheduleStops.getFirst().getTicketPrice().getCurrency();
        Money totalPrice = new Money(BigDecimal.ZERO, currency);
        for (TrainScheduleStop trainScheduleStop : trainScheduleStops) {
            totalPrice = totalPrice.add(trainScheduleStop.getTicketPrice());
        }
        return totalPrice;
    }

    @Override
    public Money calculateTicketPriceForPassengers(Money standardTicketPrice,
                                                   Integer childSeats, Integer adultSeats, Integer seniorSeats) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        if (childSeats > 0) {
            BigDecimal childPrice = standardTicketPrice.getValue()
                    .multiply(CHILD_RATE)
                    .multiply(BigDecimal.valueOf(childSeats));
            totalAmount = totalAmount.add(childPrice);
        }

        if (seniorSeats > 0) {
            BigDecimal seniorPrice = standardTicketPrice.getValue()
                    .multiply(SENIOR_RATE)
                    .multiply(BigDecimal.valueOf(seniorSeats));
            totalAmount = totalAmount.add(seniorPrice);
        }

        if (adultSeats > 0) {
            BigDecimal adultPrice = standardTicketPrice.getValue()
                    .multiply(ADULT_RATE)
                    .multiply(BigDecimal.valueOf(adultSeats));
            totalAmount = totalAmount.add(adultPrice);
        }

        return new Money(totalAmount, standardTicketPrice.getCurrency());
    }


    @Override
    public Money calculateBaseFareForRoute(Train train, StationRoute stationRoute) {
        BigDecimal baseFarePerKm = BigDecimal.valueOf(500).multiply(train.getRatePerKm());

        Double distance = stationRoute.getDistanceKm();
        BigDecimal ticketPrice = baseFarePerKm.multiply(BigDecimal.valueOf(distance));

        return new Money(ticketPrice, Currency.VND.getValue());
    }
}
