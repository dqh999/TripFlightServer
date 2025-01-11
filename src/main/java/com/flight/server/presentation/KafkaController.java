package com.flight.server.presentation;

import com.flight.server.application.utils.component.KafkaProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    private final KafkaProducer kafkaProducer;
    public KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }
    @PostMapping
    public void post() {
        kafkaProducer.send("test","hi ban!");
    }
}
