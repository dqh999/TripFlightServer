package com.example.railgo.presentation.account;

import com.example.railgo.application.account.dataTransferObject.request.ChangePasswordRequest;
import com.example.railgo.application.account.dataTransferObject.request.LoginRequest;
import com.example.railgo.application.account.dataTransferObject.request.RefreshTokenRequest;
import com.example.railgo.application.account.dataTransferObject.request.RegisterRequest;
import com.example.railgo.application.account.service.UserUseCase;
import com.example.railgo.application.utils.ApiResponse;
import com.example.railgo.infrastructure.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final UserUseCase userUseCase;

    @Autowired
    public AccountController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<?> handleRegister(@RequestBody RegisterRequest request) {
        var result = userUseCase.register(request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }

    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@RequestBody LoginRequest request) {
        var result = userUseCase.login(request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> handleChangePassword(@AuthenticationPrincipal UserDetail userRequest,
                                                  @RequestBody ChangePasswordRequest request) {
        var result = userUseCase.changePassword(userRequest, request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<?> handleRefreshToken(@RequestBody RefreshTokenRequest request) {
        var result = userUseCase.refreshToken(request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
}