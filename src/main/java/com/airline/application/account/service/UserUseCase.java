package com.airline.application.account.service;


import com.airline.application.account.dataTransferObject.AccountDTO;
import com.airline.application.account.dataTransferObject.request.ChangePasswordRequest;
import com.airline.application.account.dataTransferObject.request.LoginRequest;
import com.airline.application.account.dataTransferObject.request.RefreshTokenRequest;
import com.airline.application.account.dataTransferObject.request.RegisterRequest;
import com.airline.infrastructure.security.UserDetail;

public interface UserUseCase {
    AccountDTO login(LoginRequest request);
    AccountDTO register(RegisterRequest request);

    AccountDTO changePassword(UserDetail userRequest, ChangePasswordRequest request);

    AccountDTO refreshToken(RefreshTokenRequest request);

    UserDetail authenticate(String accessToken);
}
