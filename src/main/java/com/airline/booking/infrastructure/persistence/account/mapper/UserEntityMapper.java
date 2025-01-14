package com.airline.booking.infrastructure.persistence.account.mapper;

import com.airline.booking.domain.account.model.User;
import com.airline.booking.infrastructure.config.GlobalMapperConfig;
import com.airline.booking.infrastructure.mapper.MapperEntity;
import com.airline.booking.infrastructure.persistence.account.model.UserEntity;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface UserEntityMapper extends MapperEntity<UserEntity, User> {
}
