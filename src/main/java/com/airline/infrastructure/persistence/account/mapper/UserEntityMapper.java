package com.airline.infrastructure.persistence.account.mapper;

import com.airline.domain.account.model.User;
import com.airline.infrastructure.config.GlobalMapperConfig;
import com.airline.infrastructure.mapper.MapperEntity;
import com.airline.infrastructure.persistence.account.model.UserEntity;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface UserEntityMapper extends MapperEntity<UserEntity, User> {
}
