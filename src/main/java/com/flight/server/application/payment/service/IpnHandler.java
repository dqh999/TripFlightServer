package com.flight.server.application.payment.service;

import com.flight.server.application.payment.dataTransferObject.response.IpnResponse;

import java.util.Map;

public interface IpnHandler {
    IpnResponse process(Map<String,String> params);
}
