package com.airline.booking.application.account.service.implement;

import com.airline.booking.application.account.dataTransferObject.response.UserResponse;
import com.airline.booking.application.account.exception.AccountApplicationExceptionCode;
import com.airline.booking.application.account.mapper.AccountMapper;
import com.airline.booking.application.account.service.UserUseCase;
import com.airline.booking.infrastructure.exception.ApplicationException;
import com.airline.booking.domain.account.model.User;
import com.airline.booking.domain.account.service.IUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserUseCaseImpl implements UserUseCase {
    private final IUserService userService;
    private final AccountMapper accountMapper;

    public UserUseCaseImpl(
            IUserService userService,
            AccountMapper accountMapper
    ) {
        this.userService = userService;
        this.accountMapper = accountMapper;
    }

    @Override
    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ApplicationException(AccountApplicationExceptionCode.USER_NOT_AUTHENTICATED);
        }
        String username = authentication.getName();
        User existUser = userService.getByUserName(username);
        return accountMapper.toResponse(existUser);
    }
}
