package com.railgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;

@SpringBootApplication
public class RailGoServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RailGoServerApplication.class, args);

        Jedis jedis = new Jedis("dqhdev.io.vn", 6379);
        String segment = "seats:A-B";
        jedis.set(segment, "1");
    }
}
