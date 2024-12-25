package com.railgo.application.payment.service;

import com.railgo.application.payment.dataTransferObject.response.IpnResponse;

import java.util.Map;

public interface IpnHandler {
    IpnResponse process(Map<String,String> params);
}
