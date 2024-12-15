package com.example.railgo.presentation.account;

import com.example.railgo.application.account.dataTransferObject.request.ChangePasswordRequest;
import com.example.railgo.application.account.dataTransferObject.request.LoginRequest;
import com.example.railgo.application.account.dataTransferObject.request.RegisterRequest;
import com.example.railgo.application.account.service.UserUseCase;
import com.example.railgo.application.utils.ApiResponse;
import com.example.railgo.infrastructure.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController implements AccountOperation {
    private final UserUseCase userUseCase;

    @Autowired
    public AccountController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @Override
    public ResponseEntity<?> handleRegister(RegisterRequest request) {
        var result = userUseCase.register(request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }

    @Override
    public ResponseEntity<?> handleLogin(LoginRequest request) {
        var result = userUseCase.login(request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }

    @Override
    public ResponseEntity<?> handleChangePassword(UserDetail userRequest,
                                                  ChangePasswordRequest request) {
        var result = userUseCase.changePassword(userRequest, request);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
}