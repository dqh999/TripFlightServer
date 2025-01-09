package com.flight.server.application.account.service;


import com.flight.server.application.account.dataTransferObject.AccountDTO;
import com.flight.server.application.account.dataTransferObject.request.ChangePasswordRequest;
import com.flight.server.application.account.dataTransferObject.request.LoginRequest;
import com.flight.server.application.account.dataTransferObject.request.RefreshTokenRequest;
import com.flight.server.application.account.dataTransferObject.request.RegisterRequest;
import com.flight.server.infrastructure.security.UserDetail;

public interface UserUseCase {
    AccountDTO login(LoginRequest request);
    AccountDTO register(RegisterRequest request);

    AccountDTO changePassword(UserDetail userRequest, ChangePasswordRequest request);

    AccountDTO refreshToken(RefreshTokenRequest request);

    UserDetail authenticate(String accessToken);
}
