package com.airline.infrastructure.persistence.payment.repository;

import com.airline.infrastructure.persistence.payment.model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentEntityRepository extends JpaRepository<PaymentEntity, String> {
}
