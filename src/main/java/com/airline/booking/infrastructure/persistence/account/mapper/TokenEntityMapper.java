package com.airline.booking.infrastructure.persistence.account.mapper;

import com.airline.booking.domain.account.model.Token;
import com.airline.booking.infrastructure.config.GlobalMapperConfig;
import com.airline.booking.infrastructure.mapper.MapperEntity;
import com.airline.booking.infrastructure.persistence.account.model.TokenEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TokenEntityMapper extends MapperEntity<TokenEntity, Token> {
}
