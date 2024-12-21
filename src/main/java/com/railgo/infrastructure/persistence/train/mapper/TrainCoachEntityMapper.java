package com.railgo.infrastructure.persistence.train.mapper;

import com.railgo.domain.train.model.coach.TrainCoach;
import com.railgo.infrastructure.config.GlobalMapperConfig;
import com.railgo.infrastructure.mapper.MapperEntity;
import com.railgo.infrastructure.persistence.train.model.coach.TrainCoachEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TrainCoachEntityMapper extends MapperEntity<TrainCoachEntity, TrainCoach> {
}
