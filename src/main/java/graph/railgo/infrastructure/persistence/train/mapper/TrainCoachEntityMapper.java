package graph.railgo.infrastructure.persistence.train.mapper;

import graph.railgo.domain.train.model.coach.TrainCoach;
import graph.railgo.infrastructure.config.GlobalMapperConfig;
import graph.railgo.infrastructure.mapper.MapperEntity;
import graph.railgo.infrastructure.persistence.train.model.coach.TrainCoachEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TrainCoachEntityMapper extends MapperEntity<TrainCoachEntity, TrainCoach> {
}
