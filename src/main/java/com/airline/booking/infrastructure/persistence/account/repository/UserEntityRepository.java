package com.airline.booking.infrastructure.persistence.account.repository;

import com.airline.booking.infrastructure.persistence.account.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity,String> {
    boolean existsByPhoneNumber(String phoneNumber);

    @Query(value = "select u " +
            "from UserEntity u " +
            "where u.email = :userName or u.phoneNumber = :userName")
    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
}
