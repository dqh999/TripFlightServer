package com.railgo.domain.account.service;

import com.railgo.domain.account.model.Token;
import com.railgo.domain.account.model.User;

public interface ITokenService {
    Token generateToken(User user);

    String validateToken(String accessToken);

    Token getToken(String accessToken);
    void revokeToken(String accessToken);
    void revokeAllTokens(String userId);
}
