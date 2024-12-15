package com.example.railgo.presentation.account;

import com.example.railgo.application.account.dataTransferObject.request.LoginRequest;
import com.example.railgo.application.account.dataTransferObject.request.RegisterRequest;
import com.example.railgo.application.account.service.UserUseCase;
import com.example.railgo.domain.account.exception.AccountExceptionCode;
import com.example.railgo.domain.utils.exception.BusinessException;
import com.example.railgo.presentation.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping("/test")
    public ResponseEntity<?> test(){
        userUseCase.testException();
        return ApiResponse.build()
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
    public ResponseEntity<?> handleUserName(String userName) {
        var result = userUseCase.getUserByUsername(userName);
        return ApiResponse.build()
                .withData(result)
                .toEntity();
    }
}