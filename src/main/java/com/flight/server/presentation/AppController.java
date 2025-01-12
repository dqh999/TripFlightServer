package com.flight.server.presentation;

import com.flight.server.application.utils.component.KafkaProducer;
import com.flight.server.infrastructure.exception.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    private final KafkaProducer kafkaProducer;
    public AppController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }
    @PostMapping
    public ResponseEntity<ApiResponse<String>> post(
            @RequestBody @Valid TestDTO testDTO
    ) {
//        kafkaProducer.send("test","hi ban!");
        return ApiResponse.<String>build()
                .withData("hiiii")
                .toEntity();
    }
    @GetMapping
    public String index() {
        return "Hello World";
    }
}
