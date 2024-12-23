package com.railgo.infrastructure.persistence.train.mapper;

import com.railgo.domain.train.model.schedule.TrainScheduleStop;
import com.railgo.infrastructure.config.GlobalMapperConfig;
import com.railgo.infrastructure.mapper.MapperEntity;
import com.railgo.infrastructure.persistence.train.model.schedule.TrainScheduleStopEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TrainScheduleStopEntityMapper extends MapperEntity<TrainScheduleStopEntity, TrainScheduleStop> {
}
