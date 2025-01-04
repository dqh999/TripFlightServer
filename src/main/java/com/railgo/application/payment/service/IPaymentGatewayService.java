package com.railgo.application.payment.service;

import com.railgo.application.payment.dataTransferObject.request.InitPaymentRequest;
import com.railgo.application.payment.dataTransferObject.response.InitPaymentResponse;

public interface IPaymentGatewayService {
    InitPaymentResponse init(InitPaymentRequest request);
}
