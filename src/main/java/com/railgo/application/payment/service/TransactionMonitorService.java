package com.railgo.application.payment.service;

import com.railgo.application.component.KafkaProducer;
import com.railgo.application.payment.dataTransferObject.response.InitPaymentResponse;
import com.railgo.domain.payment.model.Payment;
import com.railgo.domain.payment.service.IPaymentService;
import com.railgo.domain.ticket.model.Ticket;
import com.railgo.domain.ticket.service.ITicketService;
import com.railgo.domain.utils.exception.BusinessException;
import com.railgo.infrastructure.service.cache.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class TransactionMonitorService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionMonitorService.class);

    private final ITicketService ticketService;
    private final IPaymentService paymentService;
    private final KafkaProducer kafkaProducer;
    private final JedisPool jedisPool;
    private final CacheService cacheService;

    public TransactionMonitorService(ITicketService ticketService,
                                     IPaymentService paymentService,
                                     KafkaProducer kafkaProducer,
                                     JedisPool jedisPool,
                                     CacheService cacheService) {
        this.ticketService = ticketService;
        this.paymentService = paymentService;
        this.kafkaProducer = kafkaProducer;
        this.jedisPool = jedisPool;
        this.cacheService = cacheService;
    }

    @Value("${spring.kafka.topic.ticket.ticket-cancel}")
    private String ticketCancelTopic;

    @Scheduled(fixedRate = 60000)
    public void checkFailedTransactions() {
        logger.info("Starting to check for failed transactions...");
        try (Jedis jedis = jedisPool.getResource()) {
            Set<String> keys = jedis.keys("payment_pending_*");
            for (String key : keys) {
                var payment = cacheService.get(key, InitPaymentResponse.class);
                if (payment.getExpiryTime().isBefore(LocalDateTime.now())) {
                    try {
                        Ticket ticket = ticketService.getTicketWithPaymentId(payment.getPaymentId());
                        kafkaProducer.send(ticketCancelTopic, ticket.getId());

                        logger.info("Cancelling ticket with ticket ID {}", ticket.getId());
                    } catch (BusinessException e) {
                        logger.error("Failed to rollback transaction for payment ID {}: {}", payment.getPaymentId(), e.getMessage());
                    }

                }
            }
        }
    }
}
