package com.flight.server.domain.payment.repository;

import com.flight.server.domain.payment.model.Payment;

import java.util.Optional;

public interface PaymentRepository {
    void save(Payment payment);
    Optional<Payment> findById(String id);
}
