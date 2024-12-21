package com.railgo.infrastructure.persistence.train.mapper;

import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.infrastructure.config.GlobalMapperConfig;
import com.railgo.infrastructure.mapper.MapperEntity;
import com.railgo.infrastructure.persistence.train.model.schedule.TrainScheduleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TrainScheduleEntityMapper extends MapperEntity<TrainScheduleEntity, TrainSchedule> {
}
