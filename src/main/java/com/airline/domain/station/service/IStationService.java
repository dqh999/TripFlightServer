package com.railgo.domain.station.service;

import com.railgo.domain.station.model.Station;
import org.springframework.data.domain.Page;

public interface IStationService {
    Station addStation(Station station);
    Station getStation(String id);
    Page<Station> search(String keyword, int page, int size);
}
