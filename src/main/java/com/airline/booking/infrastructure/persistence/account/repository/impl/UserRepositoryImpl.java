package com.airline.booking.infrastructure.persistence.account.repository.impl;


import com.airline.booking.domain.account.model.User;
import com.airline.booking.domain.account.repository.UserRepository;
import com.airline.booking.infrastructure.persistence.account.mapper.UserEntityMapper;
import com.airline.booking.infrastructure.persistence.account.model.UserEntity;
import com.airline.booking.infrastructure.persistence.account.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserEntityRepository repository;
    private final UserEntityMapper mapper;

    @Autowired
    public UserRepositoryImpl(UserEntityRepository repository,UserEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        return repository.existsByPhoneNumber(phoneNumber);
    }
    @Override
    public Optional<User> findByUserName(String userName) {
        return repository.findByUserName(userName)
                .map(mapper::toDTO);
    }

    @Override
    public Optional<User> findById(String id){
        if (id == null) {
            return Optional.empty();
        }
        return repository.findById(id)
                .map(mapper::toDTO);
    }
    @Override
    public User save(User user) {
        UserEntity userEntity = mapper.toEntity(user);
        repository.save(userEntity);
        return mapper.toDTO(userEntity);
    };
}
