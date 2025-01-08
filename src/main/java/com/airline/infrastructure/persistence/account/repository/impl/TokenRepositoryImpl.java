package com.airline.infrastructure.persistence.account.repository.impl;

import com.airline.domain.account.model.Token;
import com.airline.domain.account.repository.TokenRepository;
import com.airline.infrastructure.persistence.account.mapper.TokenEntityMapper;
import com.airline.infrastructure.persistence.account.model.TokenEntity;
import com.airline.infrastructure.persistence.account.repository.TokenEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void saveAll(List<Token> t) {
        List<TokenEntity> entities = t.stream().map(mapper::toEntity).collect(Collectors.toList());
        repository.saveAll(entities);
    }

    @Override
    public Optional<Token> findByValue(String value) {
        Optional<TokenEntity> existEntity = repository.findByValue(value);
        return existEntity.map(mapper::toDTO);
    }

    @Override
    public List<Token> findByAccountIdAndRevokedFalse(String accountId) {
        return repository.findByAccountIdAndRevokedFalse(accountId)
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }
}
