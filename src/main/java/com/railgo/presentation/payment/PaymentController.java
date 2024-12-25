package com.railgo.presentation.payment;

import com.railgo.application.payment.dataTransferObject.response.IpnResponse;
import com.railgo.application.payment.service.IpnHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final IpnHandler ipnHandler;

    @Autowired
    public PaymentController(IpnHandler ipnHandler) {
        this.ipnHandler = ipnHandler;
    }

    @GetMapping("/VNPay_ipn")
    public IpnResponse processIpn(@RequestParam Map<String, String> params) {
        return ipnHandler.process(params);
    }
}
