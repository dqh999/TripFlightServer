package com.railgo.infrastructure.persistence.payment.repository.impl;

import com.railgo.domain.payment.model.Payment;
import com.railgo.domain.payment.repository.PaymentRepository;
import com.railgo.infrastructure.persistence.payment.mapper.PaymentMapper;
import com.railgo.infrastructure.persistence.payment.model.PaymentEntity;
import com.railgo.infrastructure.persistence.payment.repository.PaymentEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final PaymentEntityRepository repository;
    private final PaymentMapper mapper;

    public PaymentRepositoryImpl(PaymentEntityRepository repository, PaymentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(Payment payment) {
        PaymentEntity entity = mapper.toEntity(payment);
        repository.save(entity);
    }

    @Override
    public Optional<Payment> findById(String id) {
        return repository.findById(id).map(mapper::toDTO);
    }
}
