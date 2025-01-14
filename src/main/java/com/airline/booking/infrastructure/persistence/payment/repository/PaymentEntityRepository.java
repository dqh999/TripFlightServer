package com.airline.booking.infrastructure.persistence.payment.repository;

import com.airline.booking.infrastructure.persistence.payment.model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentEntityRepository extends JpaRepository<PaymentEntity, String> {
}
