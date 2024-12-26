package com.railgo.infrastructure.service.messaging;

import com.railgo.domain.utils.exception.BusinessException;
import com.railgo.infrastructure.util.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

import java.util.Map;

@Service
public class EmailService {
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

    public void send(String to,
                     String subject,
                     String template,
                     Map<String, Object> variables) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariables(variables);
            String htmlContent = templateEngine.process(template, context);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(from);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e){
            throw new BusinessException();
        }
    }

}
