package com.railgo.presentation;


import com.railgo.infrastructure.dataTransferObject.request.EmailRequest;
import com.railgo.infrastructure.component.KafkaProducer;
import com.railgo.infrastructure.util.Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HelloWord {
    private final KafkaProducer kafkaProducer;

    @Value("${spring.kafka.topic.email}")
    private String topicEmail;

    public HelloWord(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/")
    public String hello() {
        return "index";
    }

    @PostMapping("/email")
    public void send() {
        var emailRequest = new EmailRequest(
                "dqhit999@gmail.com",
                "Your RailGo e-Ticket - Payment Successful",
                Template.TICKET_SUCCESS,
                new HashMap<>()
        );
        System.out.println(emailRequest);
        kafkaProducer.send(topicEmail, emailRequest);
    }

}
