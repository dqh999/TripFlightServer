package com.example.railgo.infrastucture.persistence.account.repository;

import com.example.railgo.infrastucture.persistence.account.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity,String> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);

    @Query(value = "SELECT new UserEntity(id,firstName,lastName,gender,dateOfBirth) " +
            "FROM UserEntity " +
            "WHERE id = :id")
    Optional<UserEntity> findById(String id);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
}
