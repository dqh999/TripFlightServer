package com.railgo.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWord {
    @GetMapping
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("Hello World");
    }
}
