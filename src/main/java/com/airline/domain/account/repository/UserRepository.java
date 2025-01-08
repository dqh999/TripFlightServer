package com.airline.domain.account.repository;

import com.airline.domain.account.model.User;

import java.util.Optional;

public interface UserRepository {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findById(String id);
    User save(User user);
}
