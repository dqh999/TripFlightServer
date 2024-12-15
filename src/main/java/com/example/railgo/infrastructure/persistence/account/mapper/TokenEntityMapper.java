package com.example.railgo.infrastructure.persistence.account.mapper;

import com.example.railgo.domain.account.model.Token;
import com.example.railgo.infrastructure.mapper.GlobalMapperConfig;
import com.example.railgo.infrastructure.mapper.MapperEntity;
import com.example.railgo.infrastructure.persistence.account.model.TokenEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        config = GlobalMapperConfig.class)
public interface TokenEntityMapper extends MapperEntity<TokenEntity, Token> {
}
