package com.airline.application.payment.service;

import com.airline.application.payment.dataTransferObject.request.InitPaymentRequest;
import com.airline.application.payment.dataTransferObject.response.InitPaymentResponse;

public interface IPaymentGatewayService {
    InitPaymentResponse init(InitPaymentRequest request);
}
