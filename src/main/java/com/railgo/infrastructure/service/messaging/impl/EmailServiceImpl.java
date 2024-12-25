package com.railgo.infrastructure.service.messaging.impl;

import com.railgo.infrastructure.service.messaging.IMessagingService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IMessagingService {
    @Override
    public void send(String to, String message) {

    }
}
