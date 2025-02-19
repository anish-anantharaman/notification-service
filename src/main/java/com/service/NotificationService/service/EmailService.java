package com.service.NotificationService.service;

import com.service.NotificationService.exceptions.EmailException;
import com.service.NotificationService.model.EmailRequest;
import com.service.NotificationService.util.Constants;
import com.service.NotificationService.util.ProjectUtil;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${smtp.mail.id}")
    private String email;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean sendEmail(EmailRequest emailRequest) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setTo(emailRequest.getToEmail());
            mailMessage.setSubject(emailRequest.getSubject());
            mailMessage.setText(emailRequest.getMessage());
            mailMessage.setFrom(email);
            javaMailSender.send(mailMessage);
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new EmailException(String.format(Constants.EXCEPTION_MESSAGE, "in sendEmail()", e.getMessage()),
                    ProjectUtil.getCurrentTimeStamp());
        }
    }

    public boolean sendTemplateEmail(EmailRequest emailRequest) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, Boolean.TRUE);
            helper.setFrom(email);
            helper.setTo(emailRequest.getToEmail());
            helper.setSubject(emailRequest.getSubject());

            Context context = new Context();
            context.setVariable(Constants.SUBJECT, emailRequest.getSubject());
            context.setVariable(Constants.MESSAGE, emailRequest.getSubject());
            context.setVariable(Constants.BUTTON_LINK, emailRequest.getButtonUrl());
            context.setVariable(Constants.BUTTON_TEXT, emailRequest.getButtonText());

            String htmlContent = springTemplateEngine.process("email-template", context);
            helper.setText(htmlContent, Boolean.TRUE);
            // send the email
            javaMailSender.send(mimeMessage);
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new EmailException(String.format(Constants.EXCEPTION_MESSAGE, "in sendTemplateEmail()", e.getMessage()),
                    ProjectUtil.getCurrentTimeStamp());
        }
    }
}
