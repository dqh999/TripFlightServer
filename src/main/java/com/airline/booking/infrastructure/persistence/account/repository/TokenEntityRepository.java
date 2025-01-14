package com.airline.booking.infrastructure.persistence.account.repository;

import com.airline.booking.infrastructure.persistence.account.model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenEntityRepository extends JpaRepository<TokenEntity,String> {
    Optional<TokenEntity> findByValue(String value);
    Optional<TokenEntity> findByRefreshToken(String refreshToken);

    @Query(value = "SELECT t FROM TokenEntity t " +
            "WHERE t.id = :accountId AND t.isRevoked = false")
    List<TokenEntity> findByAccountIdAndRevokedFalse(String accountId);
}
