package com.flight.server.domain.account.service;

import com.flight.server.domain.account.model.Token;
import com.flight.server.domain.account.model.User;

public interface ITokenService {
    Token generateToken(User user);

    String validateToken(String accessToken);

    Token getToken(String accessToken);
    void revokeToken(String accessToken);
    void revokeAllTokens(String userId);
}
