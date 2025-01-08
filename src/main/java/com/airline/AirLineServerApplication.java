package com.airline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class AirLineServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(AirLineServerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(AirLineServerApplication.class, args);
        logger.info("airlineServerApplication started");
    }
}
