package graph.railgo.infrastructure.persistence.station.repository.impl;

import graph.railgo.domain.station.model.Station;
import graph.railgo.domain.station.repository.StationRepository;
import graph.railgo.infrastructure.persistence.station.mapper.StationEntityMapper;
import graph.railgo.infrastructure.persistence.station.model.StationEntity;
import graph.railgo.infrastructure.persistence.station.repository.StationEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class StationRepositoryImpl implements StationRepository {
    private final StationEntityRepository repository;
    private final StationEntityMapper mapper;

    @Autowired
    public StationRepositoryImpl(StationEntityRepository repository,
                                 StationEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(Station s) {
        StationEntity entity = mapper.toEntity(s);
        repository.save(entity);
    }

    @Override
    public Station findById(String id) {
        Optional<StationEntity> existEntity = repository.findById(id);
        return existEntity.map(mapper::toDTO).orElse(null);
    }
}
