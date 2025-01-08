package com.railgo.infrastructure.persistence.account.mapper;

import com.railgo.domain.account.model.User;
import com.railgo.infrastructure.config.GlobalMapperConfig;
import com.railgo.infrastructure.mapper.MapperEntity;
import com.railgo.infrastructure.persistence.account.model.UserEntity;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface UserEntityMapper extends MapperEntity<UserEntity, User> {
}
