package graph.railgo.infrastructure.persistence.train.mapper;

import graph.railgo.domain.train.model.schedule.TrainSchedule;
import graph.railgo.infrastructure.config.GlobalMapperConfig;
import graph.railgo.infrastructure.mapper.MapperEntity;
import graph.railgo.infrastructure.persistence.train.model.schedule.TrainScheduleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TrainScheduleEntityMapper extends MapperEntity<TrainScheduleEntity, TrainSchedule> {
}
