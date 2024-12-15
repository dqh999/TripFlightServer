package com.example.railgo.infrastructure.persistence.account.repository.impl;

import com.example.railgo.domain.account.model.Token;
import com.example.railgo.domain.account.repository.TokenRepository;
import com.example.railgo.infrastructure.persistence.account.mapper.TokenEntityMapper;
import com.example.railgo.infrastructure.persistence.account.model.TokenEntity;
import com.example.railgo.infrastructure.persistence.account.repository.TokenEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TokenRepositoryImpl implements TokenRepository {
    private final TokenEntityRepository repository;
    private final TokenEntityMapper mapper;

    @Autowired
    public TokenRepositoryImpl(TokenEntityRepository repository,
                               TokenEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    @Override
    public void save(Token t) {
        TokenEntity entity = mapper.toEntity(t);
        repository.save(entity);
    }

    @Override
    public Optional<Token> findByValue(String value) {
        Optional<TokenEntity> existEntity = repository.findByValue(value);
        return existEntity.map(mapper::toDTO);
    }
}
