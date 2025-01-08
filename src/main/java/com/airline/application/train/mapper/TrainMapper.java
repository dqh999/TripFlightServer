package com.railgo.application.train.mapper;

import com.railgo.application.train.dataTransferObject.request.AddTrainRequest;
import com.railgo.application.train.dataTransferObject.response.TrainResponse;
import com.railgo.domain.train.model.Train;
import com.railgo.infrastructure.config.GlobalMapperConfig;
import com.railgo.infrastructure.mapper.MapperEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TrainMapper extends MapperEntity<Train, TrainResponse> {
    Train toTrain(AddTrainRequest request);
}
