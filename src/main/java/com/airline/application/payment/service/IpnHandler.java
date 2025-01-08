package com.airline.application.payment.service;

import com.airline.application.payment.dataTransferObject.response.IpnResponse;

import java.util.Map;

public interface IpnHandler {
    IpnResponse process(Map<String,String> params);
}
