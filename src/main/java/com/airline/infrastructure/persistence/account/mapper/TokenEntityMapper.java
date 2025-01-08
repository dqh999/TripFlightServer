package com.airline.infrastructure.persistence.account.mapper;

import com.airline.domain.account.model.Token;
import com.airline.infrastructure.config.GlobalMapperConfig;
import com.airline.infrastructure.mapper.MapperEntity;
import com.airline.infrastructure.persistence.account.model.TokenEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TokenEntityMapper extends MapperEntity<TokenEntity, Token> {
}
