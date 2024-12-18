package graph.railgo.infrastructure.persistence.station.repository.impl;

import graph.railgo.domain.station.model.StationRoute;
import graph.railgo.domain.station.repository.StationRouteRepository;
import graph.railgo.infrastructure.persistence.station.mapper.StationRouteEntityMapper;
import graph.railgo.infrastructure.persistence.station.model.StationRouteEntity;
import graph.railgo.infrastructure.persistence.station.repository.StationRouteEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public StationRoute findById(String id) {
        return repository.findById(id).map(mapper::toDTO).orElse(null);
    }
}
