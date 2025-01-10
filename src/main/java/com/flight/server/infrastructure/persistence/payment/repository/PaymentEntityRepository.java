package com.flight.server.infrastructure.persistence.payment.repository;

import com.flight.server.infrastructure.persistence.payment.model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentEntityRepository extends JpaRepository<PaymentEntity, String> {
}
