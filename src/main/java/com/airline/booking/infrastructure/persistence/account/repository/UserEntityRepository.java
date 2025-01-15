package com.airline.booking.infrastructure.persistence.account.repository;

import com.airline.booking.infrastructure.persistence.account.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity,String> {
    boolean existsByPhoneNumber(String phoneNumber);

    @Query(value = "select new UserEntity(id,firstName,lastName,gender,dateOfBirth) " +
            "from UserEntity " +
            "where email = :userName or phoneNumber = :userName")
    Optional<UserEntity> findByUserName(String userName);

    @Query(value = "select new UserEntity(id,firstName,lastName,gender,dateOfBirth) " +
            "from UserEntity " +
            "where id = :id")
    Optional<UserEntity> findById(String id);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
}
