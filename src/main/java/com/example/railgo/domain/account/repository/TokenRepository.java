package com.example.railgo.domain.account.repository;


import com.example.railgo.domain.account.model.Token;

import java.util.Optional;

public interface TokenRepository {
    Token save(Token t);
    Optional<Token> findByValue(String value);
}
