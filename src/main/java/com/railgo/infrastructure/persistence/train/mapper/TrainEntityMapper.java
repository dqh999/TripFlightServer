package com.railgo.infrastructure.persistence.train.mapper;

import com.railgo.domain.train.model.Train;
import com.railgo.infrastructure.config.GlobalMapperConfig;
import com.railgo.infrastructure.mapper.MapperEntity;
import com.railgo.infrastructure.persistence.train.model.TrainEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TrainEntityMapper extends MapperEntity<TrainEntity, Train> {
}
