package com.example.railgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.railgo")
public class RailGoServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RailGoServerApplication.class, args);
    }
}
