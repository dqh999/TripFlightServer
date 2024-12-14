package com.example.railgo.domain.account.service;


import com.example.railgo.domain.account.model.User;

public interface IUserService {
    User register(User user);
    User login(String phoneNumber,String password);
    void changePassword(String phoneNumber,String oldPassword,String newPassword);
    User getUserByPhoneNumber(String phoneNumber);
    User getUserById(String id);
}
