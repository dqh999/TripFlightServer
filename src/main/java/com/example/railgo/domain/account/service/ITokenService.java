package com.example.railgo.domain.account.service;

import com.example.railgo.domain.account.model.Token;
import com.example.railgo.domain.account.model.User;

public interface ITokenService {
    Token generateToken(User user);
    String  authenticate(String accessToken);
}
