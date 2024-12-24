package com.railgo.domain.ticket.service;

import com.railgo.domain.station.model.StationRoute;
import com.railgo.domain.train.model.Train;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.utils.valueObject.Money;


public interface ITicketPricingService {
    Money calculateStandardTicketPrice(TrainSchedule trainSchedule);

    Money calculateTicketPriceForPassengers(Money standardTicketPrice,
                                            Integer childSeats, Integer adultSeats, Integer seniorSeats);

    Money calculateBaseFareForRoute(Train train, StationRoute stationRoute);
}
