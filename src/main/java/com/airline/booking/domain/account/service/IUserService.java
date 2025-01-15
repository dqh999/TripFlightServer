package com.airline.booking.domain.account.service;


import com.airline.booking.domain.account.model.User;

public interface IUserService {
    User register(User user);
    User login(String userName,String password);

    User changePassword(User existUser,String oldPassword,String newPassword);

    User getByUserName(String userName);
}
