package com.railgo.application.train.mapper;

import com.railgo.application.train.dataTransferObject.response.TrainScheduleResponse;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.infrastructure.config.GlobalMapperConfig;
import com.railgo.infrastructure.mapper.MapperEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TrainScheduleMapper extends MapperEntity<TrainSchedule, TrainScheduleResponse> {
}
