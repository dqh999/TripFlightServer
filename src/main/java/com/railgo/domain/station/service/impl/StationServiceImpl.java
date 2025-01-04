package com.railgo.domain.station.service.impl;

import com.railgo.domain.station.exception.StationExceptionCode;
import com.railgo.domain.station.model.Station;
import com.railgo.domain.station.repository.StationRepository;
import com.railgo.domain.station.service.IStationService;
import com.railgo.domain.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl implements IStationService {
    private final StationRepository stationRepository;

    @Autowired
    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public Station addStation(Station station) {
        stationRepository.save(station);
        return station;
    }

    @Override
    public Station getStation(String id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(StationExceptionCode.STATION_NOT_FOUND));
    }

    @Override
    public Page<Station> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return stationRepository.search(keyword, pageable);
    }
}
