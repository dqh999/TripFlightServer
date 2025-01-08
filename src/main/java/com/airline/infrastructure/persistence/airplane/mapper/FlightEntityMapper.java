package com.airline.infrastructure.persistence.airplane.mapper;

import com.airline.domain.airplane.model.Airplane;
import com.airline.infrastructure.config.GlobalMapperConfig;
import com.airline.infrastructure.mapper.MapperEntity;
import com.airline.infrastructure.persistence.airplane.model.PlaneEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface FlightEntityMapper extends MapperEntity<PlaneEntity, Airplane> {
}
