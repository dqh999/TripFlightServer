package com.airline.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class FlightBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightBookingApplication.class, args);
    }
}
