package com.example.railgo.infrastructure.persistence.account.mapper;

import com.example.railgo.domain.account.model.User;
import com.example.railgo.infrastructure.mapper.GlobalMapperConfig;
import com.example.railgo.infrastructure.mapper.MapperEntity;
import com.example.railgo.infrastructure.persistence.account.model.UserEntity;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface UserEntityMapper extends MapperEntity<UserEntity, User> {
}
