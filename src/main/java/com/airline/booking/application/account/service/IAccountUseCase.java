package com.airline.booking.application.account.service;


import com.airline.booking.application.account.dataTransferObject.AccountDTO;
import com.airline.booking.application.account.dataTransferObject.request.*;
import com.airline.booking.infrastructure.security.UserDetail;

public interface IAccountUseCase {
    AccountDTO register(RegisterRequest request);

    AccountDTO login(LoginRequest request);

    AccountDTO loginWithOAuth2(OAuth2LoginRequest request);

    AccountDTO changePassword(UserDetail userRequest, ChangePasswordRequest request);

    AccountDTO refreshToken(RefreshTokenRequest request);

    UserDetail authenticate(String accessToken);
}
