package com.railgo.infrastructure.persistence.payment.repository;

import com.railgo.infrastructure.persistence.payment.model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentEntityRepository extends JpaRepository<PaymentEntity, String> {
}
