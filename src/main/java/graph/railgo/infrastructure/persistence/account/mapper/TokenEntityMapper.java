package graph.railgo.infrastructure.persistence.account.mapper;

import graph.railgo.domain.account.model.Token;
import graph.railgo.infrastructure.config.GlobalMapperConfig;
import graph.railgo.infrastructure.mapper.MapperEntity;
import graph.railgo.infrastructure.persistence.account.model.TokenEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TokenEntityMapper extends MapperEntity<TokenEntity, Token> {
}
