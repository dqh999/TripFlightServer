package com.railgo.infrastructure.service.messaging;

public interface IMessagingService {
    void send(String to, String message);
}
