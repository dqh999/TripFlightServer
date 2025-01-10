package com.flight.server.infrastructure.persistence.account.mapper;

import com.flight.server.domain.account.model.Token;
import com.flight.server.infrastructure.config.GlobalMapperConfig;
import com.flight.server.infrastructure.mapper.MapperEntity;
import com.flight.server.infrastructure.persistence.account.model.TokenEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TokenEntityMapper extends MapperEntity<TokenEntity, Token> {
}
