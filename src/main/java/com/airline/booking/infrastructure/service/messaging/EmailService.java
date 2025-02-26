package com.airline.booking.infrastructure.service.messaging;

import com.airline.booking.domain.exception.BusinessException;
import com.airline.booking.infrastructure.dataTransferObject.request.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;


@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public EmailService(JavaMailSender mailSender,
                        TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void send(EmailRequest request) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariables(request.getVariables());
            String htmlContent = templateEngine.process(request.getTemplate(), context);

            helper.setTo(request.getTo());
            helper.setSubject(request.getSubject());
            helper.setFrom(from);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {

            throw new BusinessException();
        }
    }

}
