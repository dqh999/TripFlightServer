package com.example.railgo.infrastucture.persistence.account.mapper;

import com.example.railgo.domain.account.model.User;
import com.example.railgo.infrastucture.mapper.GlobalMapperConfig;
import com.example.railgo.infrastucture.mapper.MapperEntity;
import com.example.railgo.infrastucture.persistence.account.model.UserEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface UserEntityMapper extends MapperEntity<UserEntity, User> {

}
