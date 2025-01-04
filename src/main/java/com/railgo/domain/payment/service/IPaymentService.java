package com.railgo.domain.payment.service;

import com.railgo.domain.payment.model.Payment;

public interface IPaymentService {
    void pay(Payment payment);
    void confirmPayment(Payment payment);
    Payment getPayment(String paymentId);
}
