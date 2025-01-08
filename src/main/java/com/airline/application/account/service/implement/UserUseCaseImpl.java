package com.airline.application.account.service.implement;

import com.airline.application.account.dataTransferObject.AccountDTO;
import com.airline.application.account.dataTransferObject.request.ChangePasswordRequest;
import com.airline.application.account.dataTransferObject.request.LoginRequest;
import com.airline.application.account.dataTransferObject.request.RefreshTokenRequest;
import com.airline.application.account.dataTransferObject.request.RegisterRequest;
import com.airline.application.account.exception.AccountApplicationExceptionCode;
import com.airline.application.account.mapper.AccountMapper;
import com.airline.application.account.service.UserUseCase;
import com.airline.application.utils.exception.ApplicationException;
import com.airline.infrastructure.mapper.ObjectMapper;
import com.airline.domain.account.component.UserValidator;
import com.airline.domain.account.model.Token;
import com.airline.domain.account.model.User;
import com.airline.domain.account.service.ITokenService;
import com.airline.domain.account.service.IUserService;
import com.airline.domain.utils.exception.BusinessException;
import com.airline.infrastructure.service.cache.CacheService;
import com.airline.infrastructure.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserUseCaseImpl implements UserUseCase {
    private final IUserService userService;
    private final ITokenService tokenService;
    private final AccountMapper accountMapper;
    private final CacheService cacheService;

    @Value("${spring.jwt.expiration}")
    private long accessTokenTTL;

    @Autowired
    public UserUseCaseImpl(IUserService userService,
                           ITokenService tokenService,
                           AccountMapper accountMapper,
                           CacheService cacheService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.accountMapper = accountMapper;
        this.cacheService = cacheService;
    }


    @Override
    public AccountDTO register(RegisterRequest request) {

        String phoneNumber = request.getPhoneNumber();
        validatePhoneNumber(phoneNumber);
        cacheService.delete(phoneNumber);

        boolean isLocked = cacheService.acquireLock(phoneNumber, 300);
        if (!isLocked) {
            throw new ApplicationException(AccountApplicationExceptionCode.LOCK_ALREADY_ACQUIRED);
        }
        try {
            User userRegister = accountMapper.toDTO(request);
            User newUser = userService.register(userRegister);

            Token token = tokenService.generateToken(newUser);
            String accessToken = token.getValue();

            AccountDTO accountDTO = AccountDTO.from(newUser, token);

            storeAccessTokenInCache(accessToken, accountDTO);

            return accountDTO;
        } catch (BusinessException e) {
            throw new BusinessException(e.getExceptionCode());
        } finally {
            cacheService.releaseLock(phoneNumber);
        }
    }

    private void storeAccessTokenInCache(String accessToken, AccountDTO accountDTO) {
        cacheService.put(accessToken, accountDTO, accessTokenTTL);
    }

    @Override
    public AccountDTO login(LoginRequest request) {
        User existUser = userService.login(request.getPhoneNumber(), request.getPassword());

        Token token = tokenService.generateToken(existUser);
        String accessToken = token.getValue();

        AccountDTO accountDTO = AccountDTO.from(existUser, token);

        storeAccessTokenInCache(accessToken, accountDTO);

        return accountDTO;
    }

    private void validatePhoneNumber(String phoneNumber) {
        UserValidator validator = new UserValidator();
        validator.validatePhoneNumber(phoneNumber);
    }

    @Override
    public AccountDTO changePassword(UserDetail userRequest,
                                     ChangePasswordRequest request) {
        String phoneNumber = userRequest.getUsername();

        User userResult = userService.changePassword(phoneNumber, request.getOldPassword(), request.getNewPassword());

        tokenService.revokeAllTokens(userResult.getId());

        Token token = tokenService.generateToken(userResult);
        String accessToken = token.getValue();

        AccountDTO accountDTO = AccountDTO.from(userResult, token);

        storeAccessTokenInCache(accessToken, accountDTO);

        return accountDTO;
    }

    @Override
    public AccountDTO refreshToken(RefreshTokenRequest request) {

        return null;
    }

    @Override
    public UserDetail authenticate(String accessToken) {
        String userName = tokenService.validateToken(accessToken);
        if (userName == null) {
            throw new ApplicationException(AccountApplicationExceptionCode.INVALID);
        }

        if (cacheService.exists(accessToken)) {
            AccountDTO accountDTO = cacheService.get(accessToken,AccountDTO.class);
            return accountMapper.toEntity(accountDTO);
        }
        User existUser = userService.getUserByPhoneNumber(userName);
        return accountMapper.toEntity(existUser);
    }
}
