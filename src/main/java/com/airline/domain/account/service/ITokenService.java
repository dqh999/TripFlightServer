package com.airline.domain.account.service;

import com.airline.domain.account.model.Token;
import com.airline.domain.account.model.User;

public interface ITokenService {
    Token generateToken(User user);

    String validateToken(String accessToken);

    Token getToken(String accessToken);
    void revokeToken(String accessToken);
    void revokeAllTokens(String userId);
}
