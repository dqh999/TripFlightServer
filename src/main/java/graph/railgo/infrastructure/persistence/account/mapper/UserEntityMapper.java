package graph.railgo.infrastructure.persistence.account.mapper;

import graph.railgo.domain.account.model.User;
import graph.railgo.infrastructure.config.GlobalMapperConfig;
import graph.railgo.infrastructure.mapper.MapperEntity;
import graph.railgo.infrastructure.persistence.account.model.UserEntity;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface UserEntityMapper extends MapperEntity<UserEntity, User> {
}
