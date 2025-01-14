package com.airline.booking.domain.account.repository;


import com.airline.booking.domain.account.model.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository {
    void save(Token t);
    void saveAll(List<Token> t);
    Optional<Token> findByValue(String value);
    Optional<Token> findByRefreshToken(String refreshToken);
    List<Token> findByAccountIdAndRevokedFalse(String accountId);
}
