package com.service.NotificationService.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.logging.Logger;

@Service

public class EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;
    private final Logger LOGGER = Logger.getLogger(EmailService.class.getName());


    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public boolean sendEmail(String toEmail, String subject, String message) {

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setTo(toEmail);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailMessage.setFrom("anish.pgt@gmail.com");
            javaMailSender.send(mailMessage);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOGGER.severe("Error while sending email in sendEmail method : " + e.getMessage());
            return Boolean.FALSE;
        }
    }

    @Async
    public boolean sendTemplateEmail(String toEmail, String subject, String message, String buttonUrl) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);

            Context context = new Context();

            context.setVariable("subject", subject);
            context.setVariable("message", message);
            context.setVariable("buttonLink", buttonUrl);

            String htmlContent = springTemplateEngine.process("email-template", context);
            helper.setText(htmlContent, true);

            // Send the email
            javaMailSender.send(mimeMessage);

            return Boolean.TRUE;
        } catch (Exception e) {
            LOGGER.severe("Error while sending email in sendTemplateEmail method : " + e.getMessage());
            return Boolean.FALSE;
        }
    }
}
