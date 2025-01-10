package com.flight.server.infrastructure.persistence.account.mapper;

import com.flight.server.domain.account.model.User;
import com.flight.server.infrastructure.config.GlobalMapperConfig;
import com.flight.server.infrastructure.mapper.MapperEntity;
import com.flight.server.infrastructure.persistence.account.model.UserEntity;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface UserEntityMapper extends MapperEntity<UserEntity, User> {
}
