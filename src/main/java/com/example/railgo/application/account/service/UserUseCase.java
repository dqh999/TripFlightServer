package com.example.railgo.application.account.service;


import com.example.railgo.application.account.dataTransferObject.AccountDTO;
import com.example.railgo.application.account.dataTransferObject.UserDetail;
import com.example.railgo.application.account.dataTransferObject.request.LoginRequest;
import com.example.railgo.application.account.dataTransferObject.request.RegisterRequest;

public interface UserUseCase {
    AccountDTO login(LoginRequest request);
    AccountDTO register(RegisterRequest request);
    UserDetail getUserByUsername(String username);
    UserDetail authenticate(String accessToken);
    void testException();
}
