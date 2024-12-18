package graph.railgo.domain.station.service.impl;

import graph.railgo.domain.station.model.Station;
import graph.railgo.domain.station.repository.StationRepository;
import graph.railgo.domain.station.service.IStationService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return stationRepository.findById(id);
    }
}
