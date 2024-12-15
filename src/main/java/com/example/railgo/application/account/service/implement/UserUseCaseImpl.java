package com.example.railgo.application.account.service.implement;

import com.example.railgo.application.account.dataTransferObject.AccountDTO;
import com.example.railgo.application.account.dataTransferObject.request.ChangePasswordRequest;
import com.example.railgo.application.account.dataTransferObject.request.LoginRequest;
import com.example.railgo.application.account.dataTransferObject.request.RegisterRequest;
import com.example.railgo.application.account.exception.AccountApplicationExceptionCode;
import com.example.railgo.application.account.mapper.AccountMapper;
import com.example.railgo.application.account.service.UserUseCase;
import com.example.railgo.application.exception.ApplicationException;
import com.example.railgo.application.utils.ObjectMapperUtil;
import com.example.railgo.domain.account.component.UserValidator;
import com.example.railgo.domain.account.model.Token;
import com.example.railgo.domain.account.model.User;
import com.example.railgo.domain.account.service.ITokenService;
import com.example.railgo.domain.account.service.IUserService;
import com.example.railgo.domain.utils.exception.BusinessException;
import com.example.railgo.infrastructure.cache.CacheService;
import com.example.railgo.infrastructure.security.UserDetail;
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
            if (cacheService.exists(phoneNumber)) {
                throw new ApplicationException(AccountApplicationExceptionCode.RESOURCE_EXISTS);
            }

            cacheService.put(phoneNumber, "REGISTER_IN_PROGRESS");

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
            cacheService.delete(phoneNumber);
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
    public UserDetail authenticate(String accessToken) {
        String userName = tokenService.validateToken(accessToken);
        if (userName == null) {
            throw new ApplicationException(AccountApplicationExceptionCode.INVALID);
        }

        if (cacheService.exists(accessToken)) {
            Object cachedObject = cacheService.get(accessToken);
            AccountDTO accountDTO = ObjectMapperUtil.convert(cachedObject, AccountDTO.class);
            return accountMapper.toEntity(accountDTO);
        }
        User existUser = userService.getUserByPhoneNumber(userName);
        return accountMapper.toEntity(existUser);
    }
}
