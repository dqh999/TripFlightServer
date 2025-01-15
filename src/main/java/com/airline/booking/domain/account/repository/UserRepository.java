package com.airline.booking.domain.account.repository;

import com.airline.booking.domain.account.model.User;

import java.util.Optional;

public interface UserRepository {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByUserName(String userName);
    Optional<User> findById(String id);
    User save(User user);
}
