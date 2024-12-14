package com.example.railgo.infrastucture.persistence.account.repository;

import com.example.railgo.infrastucture.persistence.account.model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenEntityRepository extends JpaRepository<TokenEntity,String> {
}
