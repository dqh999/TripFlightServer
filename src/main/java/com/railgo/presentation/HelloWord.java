package com.railgo.presentation;


import com.railgo.infrastructure.dataTransferObject.request.EmailRequest;
import com.railgo.infrastructure.component.KafkaProducer;
import com.railgo.infrastructure.persistence.test.Test;
import com.railgo.infrastructure.persistence.test.TestRepository;
import com.railgo.infrastructure.util.Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.HashMap;

@RestController
public class HelloWord {
    private final TestRepository testRepository;
    private final KafkaProducer kafkaProducer;

    @Value("${spring.kafka.topic.email}")
    private String topicEmail;

    public HelloWord(KafkaProducer kafkaProducer,
                     TestRepository testRepository) {
        this.kafkaProducer = kafkaProducer;
        this.testRepository = testRepository;
    }

    @GetMapping("/")
    public String hello() {
        return "index";
    }


    @PostMapping
    public void RedisOptimisticLocking() throws InterruptedException {
        try (Jedis jedis = new Jedis("dqhdev.io.vn", 6379)) {
            String segment = "seats:A-B";

            if (jedis.get(segment) == null) {
                jedis.set(segment, "1");
                System.out.println("Initialized seats available: " + jedis.get(segment));
            }
            // Bắt đầu theo dõi số ghế hiện tại
            jedis.watch(segment);

            // Lấy số ghế khả dụng ban đầu
            int availableSeats = Integer.parseInt(jedis.get(segment));

            Thread.sleep( 3000);
            int seatsToBook = 1;
            // Kiểm tra số ghế khả dụng
            if (availableSeats >= seatsToBook) {
                // Bắt đầu giao dịch (transaction)
                var transaction = jedis.multi();
                transaction.decrBy(segment, seatsToBook); // Giảm số ghế
                var result = transaction.exec(); // Thực hiện giao dịch

                if (result == null) {
                    // Nếu exec trả về null, có nghĩa là số ghế đã thay đổi (xung đột)
                    System.out.println("The seats have already been booked by another user. Please try again.");
                } else {
                    // Đặt vé thành công
                    System.out.println("Booking successful! " + seatsToBook + " seat(s) booked.");
                }
            } else {
                System.out.println("Not enough seats available.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @PostMapping("/retry")
    public void bookSeatsWithRetry() throws InterruptedException {
        try (Jedis jedis = new Jedis("dqhdev.io.vn", 6379)) {
            int maxRetries = 2; // Số lần retry tối đa
            int retryCount = 0;
            boolean bookingSuccessful = false;

            String segment = "seats:A-B";
            while (retryCount < maxRetries && !bookingSuccessful) {
                retryCount++;
                try {
                    // Bắt đầu theo dõi số ghế hiện tại
                    jedis.watch(segment);

                    // Lấy số ghế khả dụng ban đầu
                    int availableSeats = Integer.parseInt(jedis.get(segment));
                    System.out.println("Attempt " + retryCount + ": Current available seats: " + availableSeats);

                    Thread.sleep(5000);
                    int seatsToBook = 1; // Số ghế cần đặt
                    if (availableSeats >= seatsToBook) {
                        // Bắt đầu giao dịch (transaction)
                        var transaction = jedis.multi();
                        transaction.decrBy(segment, seatsToBook); // Giảm số ghế
                        var result = transaction.exec(); // Thực hiện giao dịch

                        if (result == null) {
                            // Nếu exec trả về null, có nghĩa là số ghế đã thay đổi (xung đột)
                            System.out.println("The seats have already been booked by another user. Retrying...");
                        } else {
                            // Đặt vé thành công
                            System.out.println("Booking successful! " + seatsToBook + " seat(s) booked.");
                            bookingSuccessful = true;
                        }
                    } else {
                        System.out.println("Not enough seats available. Retrying...");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    jedis.unwatch(); // Hủy theo dõi bất kể thành công hay thất bại
                }

                if (!bookingSuccessful) {
                    Thread.sleep(500); // Chờ 500ms trước khi retry
                }
            }

            if (!bookingSuccessful) {
                System.out.println("Failed to book seats after " + retryCount + " attempts. Please try again later.");
            }
        }
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
