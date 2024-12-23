package com.railgo.infrastructure.persistence.ticket.mapper;

import com.railgo.domain.ticket.model.Passenger;
import com.railgo.infrastructure.config.GlobalMapperConfig;
import com.railgo.infrastructure.mapper.MapperEntity;
import com.railgo.infrastructure.persistence.ticket.model.PassengerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface PassengerEntityMapper extends MapperEntity<PassengerEntity, Passenger> {
}
