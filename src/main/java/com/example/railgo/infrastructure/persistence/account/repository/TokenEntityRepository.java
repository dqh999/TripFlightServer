package com.example.railgo.infrastructure.persistence.account.repository;

import com.example.railgo.infrastructure.persistence.account.model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenEntityRepository extends JpaRepository<TokenEntity,String> {
    Optional<TokenEntity> findByValue(String value);
}
