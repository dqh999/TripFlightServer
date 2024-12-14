package com.example.railgo.application.account.service.implement;

import com.example.railgo.application.account.dataTransferObject.AccountDTO;
import com.example.railgo.application.account.dataTransferObject.UserDetail;
import com.example.railgo.application.account.service.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserUseCase userUseCase;

    @Autowired
    public UserDetailServiceImpl(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    public UserDetails authenticate(String accessToken) {
        AccountDTO accountDTO = userUseCase.authenticate(accessToken);
        return new UserDetail(accountDTO.getId(), accountDTO.getRole(), accountDTO.getPhoneNumber(), "");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userUseCase.getUserByUsername(username);
    }
}
