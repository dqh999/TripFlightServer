package com.airline.domain.payment.repository;

import com.airline.domain.payment.model.Payment;

import java.util.Optional;

public interface PaymentRepository {
    void save(Payment payment);
    Optional<Payment> findById(String id);
}
