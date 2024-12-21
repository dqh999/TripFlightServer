package com.railgo.infrastructure.persistence.ticket.repository;

import com.railgo.infrastructure.persistence.ticket.model.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountEntityRepository extends JpaRepository<DiscountEntity, String> {
}
