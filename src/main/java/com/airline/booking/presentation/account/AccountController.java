package com.airline.booking.presentation.account;

import com.airline.booking.application.account.dataTransferObject.AccountDTO;
import com.airline.booking.application.account.dataTransferObject.request.*;
import com.airline.booking.application.account.service.IAccountUseCase;
import com.airline.booking.application.account.service.IOAuth2Service;
import com.airline.booking.application.account.type.OAuth2Type;
import com.airline.booking.application.utils.RequestUtil;
import com.airline.booking.infrastructure.exception.ApiResponse;
import com.airline.booking.infrastructure.security.UserDetail;
import com.airline.booking.presentation.account.operations.AccountOperations;
import com.airline.booking.presentation.account.operations.OAuth2Operations;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.prefix}/account")
@Tag(name = "Account Controller")
public class AccountController implements AccountOperations, OAuth2Operations {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final IAccountUseCase accountUseCase;
    private final IOAuth2Service oauth2Service;

    public AccountController(IAccountUseCase accountUseCase, IOAuth2Service oauth2Service) {
        this.accountUseCase = accountUseCase;
        this.oauth2Service = oauth2Service;
    }

    @Override
    public ResponseEntity<ApiResponse<AccountDTO>> handleRegister(
            RegisterRequest request,
            HttpServletRequest httpServletRequest
    ) {
        String ipAddress = RequestUtil.getIpAddress(httpServletRequest);
        request.setIpAddress(ipAddress);

        var result = accountUseCase.register(request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }

    @Override
    public ResponseEntity<ApiResponse<AccountDTO>> handleLogin(
            LoginRequest request,
            HttpServletRequest httpServletRequest
    ) {
        String ipAddress = RequestUtil.getIpAddress(httpServletRequest);
        request.setIpAddress(ipAddress);
        var result = accountUseCase.login(request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> handleLogout(LogoutRequest request) {
        accountUseCase.logout(request);
        return ApiResponse.<Void>build().toEntity();
    }

    @Override
    public ResponseEntity<ApiResponse<String>> generateOAuth2LoginUrl(OAuth2Type type) {
        var result = oauth2Service.generateAuthUrl(type);
        return ApiResponse.<String>build()
                .withData(result)
                .toEntity();
    }

    @Override
    public ResponseEntity<ApiResponse<AccountDTO>> processOAuth2Callback(OAuth2Type type, String code) {
        var result = oauth2Service.authenticate(type, code);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }


    @Override
    public ResponseEntity<ApiResponse<AccountDTO>> handleChangePassword(
            UserDetail userRequest,
            ChangePasswordRequest request
    ) {
        var result = accountUseCase.changePassword(userRequest, request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }

    @Override
    public ResponseEntity<ApiResponse<AccountDTO>> handleRefreshToken(RefreshTokenRequest request) {
        var result = accountUseCase.refreshToken(request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }
}