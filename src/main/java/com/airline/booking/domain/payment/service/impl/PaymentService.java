package com.airline.booking.domain.payment.service.impl;

import com.airline.booking.domain.payment.model.Payment;
import com.airline.booking.domain.payment.repository.PaymentRepository;
import com.airline.booking.domain.payment.service.IPaymentService;
import com.airline.booking.domain.payment.type.PaymentStatus;
import com.airline.booking.domain.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService implements IPaymentService {
    private final PaymentRepository paymentRepository;
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    @Override
    public void pay(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public void confirmPayment(Payment payment) {
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        paymentRepository.save(payment);
    }

    @Override
    public void cancelPayment(Payment payment) {
        payment.setStatus(PaymentStatus.FAILED.getValue());
        paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getFailedPayments() {
        return List.of();
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new BusinessException(""));
    }
}
