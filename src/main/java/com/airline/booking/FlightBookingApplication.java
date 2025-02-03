package com.airline.booking;

import com.airline.booking.application.account.service.implement.AccountUseCaseImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class FlightBookingApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(FlightBookingApplication.class, args);
    }
}
