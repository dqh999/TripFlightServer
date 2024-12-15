package com.example.railgo.application.account.service;


import com.example.railgo.application.account.dataTransferObject.AccountDTO;
import com.example.railgo.application.account.dataTransferObject.request.ChangePasswordRequest;
import com.example.railgo.application.account.dataTransferObject.request.LoginRequest;
import com.example.railgo.application.account.dataTransferObject.request.RefreshTokenRequest;
import com.example.railgo.application.account.dataTransferObject.request.RegisterRequest;
import com.example.railgo.infrastructure.security.UserDetail;

public interface UserUseCase {
    AccountDTO login(LoginRequest request);
    AccountDTO register(RegisterRequest request);

    AccountDTO changePassword(UserDetail userRequest, ChangePasswordRequest request);

    AccountDTO refreshToken(RefreshTokenRequest request);

    UserDetail authenticate(String accessToken);
}
