package graph.railgo.infrastructure.persistence.train.mapper;

import graph.railgo.domain.train.model.Train;
import graph.railgo.infrastructure.config.GlobalMapperConfig;
import graph.railgo.infrastructure.mapper.MapperEntity;
import graph.railgo.infrastructure.persistence.train.model.TrainEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TrainEntityMapper extends MapperEntity<TrainEntity, Train> {
}
