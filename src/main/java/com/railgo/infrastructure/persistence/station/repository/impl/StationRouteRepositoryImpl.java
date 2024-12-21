package com.railgo.infrastructure.persistence.station.repository.impl;

import com.railgo.domain.station.model.StationRoute;
import com.railgo.domain.station.repository.StationRouteRepository;
import com.railgo.infrastructure.persistence.station.mapper.StationRouteEntityMapper;
import com.railgo.infrastructure.persistence.station.model.StationRouteEntity;
import com.railgo.infrastructure.persistence.station.repository.StationRouteEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class StationRouteRepositoryImpl implements StationRouteRepository {
    private final StationRouteEntityRepository repository;
    private final StationRouteEntityMapper mapper;

    @Autowired
    public StationRouteRepositoryImpl(StationRouteEntityRepository repository, StationRouteEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(StationRoute r) {
        StationRouteEntity newEntity = mapper.toEntity(r);
        repository.save(newEntity);
    }
    @Override
    public boolean existDirectRouteBetweenStations(String stationAId, String stationBId){
        return repository.existDirectRouteBetweenStations(stationAId,stationBId);
    };
    @Override
    public List<StationRoute> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StationRoute> findRouteBetweenStations(String stationAId, String stationBId) {
        return repository.findRouteBetweenStations(stationAId,stationBId)
                .map(mapper::toDTO);
    }

    @Override
    public StationRoute findById(String id) {
        return repository.findById(id).map(mapper::toDTO).orElse(null);
    }
}
