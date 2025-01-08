package com.railgo.domain.payment.repository;

import com.railgo.domain.payment.model.Payment;

import java.util.Optional;

public interface PaymentRepository {
    void save(Payment payment);
    Optional<Payment> findById(String id);
}
