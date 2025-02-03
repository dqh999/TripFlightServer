package com.airline.booking.presentation.payment;

import com.airline.booking.application.payment.dataTransferObject.response.IpnResponse;
import com.airline.booking.application.payment.service.IpnHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/${api.prefix}/payment")
@Tag(name = "Payment Controller")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final IpnHandler ipnHandler;

    @Autowired
    public PaymentController(IpnHandler ipnHandler) {
        this.ipnHandler = ipnHandler;
    }

    @GetMapping("/VNPay_ipn")
    public IpnResponse processIpn(@RequestParam Map<String, String> params) {
        logger.info("Received IPN request with params: {}", params);
        return ipnHandler.process(params);
    }
}
