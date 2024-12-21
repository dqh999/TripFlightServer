package com.railgo.domain.station.service;

import com.railgo.domain.station.model.Station;

public interface IStationService {
    Station addStation(Station station);
    Station getStation(String id);
}
