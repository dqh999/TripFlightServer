package graph.railgo.application.account.service;


import graph.railgo.application.account.dataTransferObject.AccountDTO;
import graph.railgo.application.account.dataTransferObject.request.ChangePasswordRequest;
import graph.railgo.application.account.dataTransferObject.request.LoginRequest;
import graph.railgo.application.account.dataTransferObject.request.RefreshTokenRequest;
import graph.railgo.application.account.dataTransferObject.request.RegisterRequest;
import graph.railgo.infrastructure.security.UserDetail;

public interface UserUseCase {
    AccountDTO login(LoginRequest request);
    AccountDTO register(RegisterRequest request);

    AccountDTO changePassword(UserDetail userRequest, ChangePasswordRequest request);

    AccountDTO refreshToken(RefreshTokenRequest request);

    UserDetail authenticate(String accessToken);
}
