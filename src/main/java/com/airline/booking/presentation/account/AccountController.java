package com.airline.booking.presentation.account;

import com.airline.booking.application.account.dataTransferObject.AccountDTO;
import com.airline.booking.application.account.dataTransferObject.request.ChangePasswordRequest;
import com.airline.booking.application.account.dataTransferObject.request.LoginRequest;
import com.airline.booking.application.account.dataTransferObject.request.RefreshTokenRequest;
import com.airline.booking.application.account.dataTransferObject.request.RegisterRequest;
import com.airline.booking.application.account.service.AccountUseCase;
import com.airline.booking.application.utils.RequestUtil;
import com.airline.booking.infrastructure.exception.ApiResponse;
import com.airline.booking.infrastructure.security.UserDetail;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.prefix}/account")
@Tag(name = "Account Controller")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountUseCase accountUseCase;

    @Autowired
    public AccountController(AccountUseCase accountUseCase) {
        this.accountUseCase = accountUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AccountDTO>> handleRegister(
            @RequestBody RegisterRequest request,
            HttpServletRequest httpServletRequest
    ) {
        String ipAddress = RequestUtil.getIpAddress(httpServletRequest);
        request.setIpAddress(ipAddress);

        var result = accountUseCase.register(request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AccountDTO>> handleLogin(
            @RequestBody LoginRequest request,
            HttpServletRequest httpServletRequest
    ) {
        String ipAddress = RequestUtil.getIpAddress(httpServletRequest);
        var result = accountUseCase.login(request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<String>> handleGetUser(
            @PathVariable String userId
    ){

        return ApiResponse.<String>build()
                .withData(userId)
                .toEntity();
    }

    @PatchMapping("/changePassword")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<AccountDTO>> handleChangePassword(
            @AuthenticationPrincipal UserDetail userRequest,
            @RequestBody ChangePasswordRequest request
    ) {
        var result = accountUseCase.changePassword(userRequest, request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<ApiResponse<AccountDTO>> handleRefreshToken(
            @RequestBody RefreshTokenRequest request
    ) {
        var result = accountUseCase.refreshToken(request);
        return ApiResponse.<AccountDTO>build()
                .withData(result)
                .toEntity();
    }
}