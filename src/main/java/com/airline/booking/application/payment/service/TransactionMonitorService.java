package com.airline.booking.application.payment.service;

import com.airline.booking.application.component.KafkaProducer;
import com.airline.booking.domain.ticket.model.Ticket;
import com.airline.booking.infrastructure.service.cache.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class TransactionMonitorService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionMonitorService.class);

    private final CacheService cacheService;
    private final KafkaProducer kafkaProducer;

    public TransactionMonitorService(CacheService cacheService,
                                     KafkaProducer kafkaProducer) {
        this.cacheService = cacheService;
        this.kafkaProducer = kafkaProducer;
    }

    @Value("${spring.kafka.topic.ticket.ticket-cancel}")
    private String ticketCancelTopic;
    @Value("${spring.data.redis.key.ticket.ticket-payment.value}")
    private String paymentKey;

    @Scheduled(fixedRate = 60000)
    public void checkFailedTransactions() {
        logger.info("Starting to check for failed transactions...");
        Set<String> keys = cacheService.keys(paymentKey);
        for (String key : keys) {
           var ticket = cacheService.get(key, Ticket.class);
           if (ticket.getExpirationTime().isBefore(LocalDateTime.now())) {
               kafkaProducer.send(ticketCancelTopic, ticket);
           }
        }
    }
}
