package com.airline.booking.application.account.service;


import com.airline.booking.application.account.dataTransferObject.AccountDTO;
import com.airline.booking.application.account.dataTransferObject.request.ChangePasswordRequest;
import com.airline.booking.application.account.dataTransferObject.request.LoginRequest;
import com.airline.booking.application.account.dataTransferObject.request.RefreshTokenRequest;
import com.airline.booking.application.account.dataTransferObject.request.RegisterRequest;
import com.airline.booking.infrastructure.security.UserDetail;

public interface AccountUseCase {
    AccountDTO login(LoginRequest request);
    AccountDTO register(RegisterRequest request);

    AccountDTO changePassword(UserDetail userRequest, ChangePasswordRequest request);

    AccountDTO refreshToken(RefreshTokenRequest request);

    UserDetail authenticate(String accessToken);
}
