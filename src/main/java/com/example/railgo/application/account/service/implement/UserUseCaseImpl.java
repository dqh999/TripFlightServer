package com.example.railgo.application.account.service.implement;

import com.example.railgo.application.account.dataTransferObject.AccountDTO;
import com.example.railgo.application.account.dataTransferObject.UserDetail;
import com.example.railgo.application.account.dataTransferObject.request.LoginRequest;
import com.example.railgo.application.account.dataTransferObject.request.RegisterRequest;
import com.example.railgo.application.account.mapper.AccountMapper;
import com.example.railgo.application.account.service.UserUseCase;
import com.example.railgo.domain.account.model.Token;
import com.example.railgo.domain.account.model.User;
import com.example.railgo.domain.account.service.ITokenService;
import com.example.railgo.domain.account.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserUseCaseImpl implements UserUseCase {
    private final IUserService userService;
    private final ITokenService tokenService;
    private final AccountMapper accountMapper;

    public UserUseCaseImpl(IUserService userService, ITokenService tokenService, AccountMapper accountMapper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.accountMapper = accountMapper;
    }

    @Override
    public void testException() {
        userService.testException();
    }

    @Override
    public AccountDTO login(LoginRequest request) {
        User existUser = userService.login(request.getPhoneNumber(), request.getPassword());
        Token token = tokenService.generateToken(existUser);
        return AccountDTO.from(existUser, token);
    }

    @Override
    public AccountDTO register(RegisterRequest request) {
        User userRegister = accountMapper.toDTO(request);

        User newUser = userService.register(userRegister);
        Token token = tokenService.generateToken(newUser);
        return AccountDTO.from(newUser, token);
    }

    @Override
    public UserDetail getUserByUsername(String username) {
        User existUser = userService.getUserByPhoneNumber(username);
        return new UserDetail(existUser.getId(), existUser.getRole(), existUser.getPhoneNumber(), existUser.getPassword());
    }

    @Override
    public UserDetail authenticate(String accessToken) {
        String userName = tokenService.authenticate(accessToken);
        User existUser = userService.getUserByPhoneNumber(userName);
        return new UserDetail(existUser.getId(), existUser.getRole(), existUser.getPhoneNumber(), "");
    }
}
