package com.railgo.domain.ticket.service;

import com.railgo.domain.station.model.StationRoute;
import com.railgo.domain.train.model.Train;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.utils.valueObject.Money;

public interface ITicketPricingService {
    Money calculateScheduleTicketPrice(TrainSchedule trainSchedule, String startStationId, String endStationId);

    Money calculateTicketPrice(Train train, StationRoute stationRoute);
}
