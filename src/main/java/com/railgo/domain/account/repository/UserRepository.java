package com.railgo.domain.account.repository;

import com.railgo.domain.account.model.User;

import java.util.Optional;

public interface UserRepository {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findById(String id);
    User save(User user);
}
