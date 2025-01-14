package com.airline.booking.domain.account.service;

import com.airline.booking.domain.account.model.Token;
import com.airline.booking.domain.account.model.User;

public interface ITokenService {
    Token createToken(User user);

    Token refreshToken(User user, String refreshToken);

    String validateToken(String accessToken);

    Token getToken(String accessToken);

    void revokeToken(String accessToken);

    void revokeAllTokens(String userId);
}
