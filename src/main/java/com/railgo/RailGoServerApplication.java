package com.railgo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class RailGoServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(RailGoServerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(RailGoServerApplication.class, args);
        logger.info("RailGoServerApplication started");
    }
}
