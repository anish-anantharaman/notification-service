package com.service.NotificationService.service.impl;

import com.service.NotificationService.exception.EmailServiceException;
import com.service.NotificationService.model.EmailRequest;
import com.service.NotificationService.service.EmailService;
import com.service.NotificationService.util.Constants;
import com.service.NotificationService.util.ProjectUtil;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final String email;
    private final SpringTemplateEngine springTemplateEngine;
    private final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, SpringTemplateEngine springTemplateEngine,
                            @Value("${smtp.mail.id}") String email) {
        this.javaMailSender = javaMailSender;
        this.email = email;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    public boolean sendEmail(EmailRequest emailRequest) {
        try {

            int count = 0;
            if (emailRequest.getToEmailList().isEmpty()) {
                throw new EmailServiceException("Recipient email list is empty", ProjectUtil.getCurrentTimeStamp());
            }

            for(String recipientEmail : emailRequest.getToEmailList()) {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(recipientEmail);
                mailMessage.setSubject(emailRequest.getSubject());
                mailMessage.setText(emailRequest.getMessage());
                mailMessage.setFrom(email);
                javaMailSender.send(mailMessage);
                ++count;

                // limit number of messages per second
                if(count % 40 == 0) {
                    Thread.sleep(2000);
                }

            }

            LOGGER.info(Constants.NOTIFICATION_SUCCESSFUL_MESSAGE);
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new EmailServiceException(String.format(Constants.EXCEPTION_MESSAGE, "sendEmail()", e.getMessage()),
                    ProjectUtil.getCurrentTimeStamp());
        }
    }

    @Override
    public boolean sendTemplateEmail(EmailRequest emailRequest) {
        try {
            int count = 0;
            if(emailRequest.getButtonUrl() == null || emailRequest.getButtonUrl().isEmpty()
                    || emailRequest.getButtonText() == null || emailRequest.getButtonText().isEmpty()) {
                throw new EmailServiceException(String.format(Constants.EXCEPTION_MESSAGE, "sendTemplateEmail()",
                        "buttonUrl and/or buttonText are null and/or empty"), ProjectUtil.getCurrentTimeStamp());
            }

            if (emailRequest.getToEmailList().isEmpty()) {
                throw new EmailServiceException("Recipient email list is empty", ProjectUtil.getCurrentTimeStamp());
            }

            Context context = new Context();
            context.setVariable(Constants.SUBJECT, emailRequest.getSubject());
            context.setVariable(Constants.MESSAGE, emailRequest.getSubject());
            context.setVariable(Constants.BUTTON_LINK, emailRequest.getButtonUrl());
            context.setVariable(Constants.BUTTON_TEXT, emailRequest.getButtonText());

            String htmlContent = springTemplateEngine.process("email-template", context);

            for(String recipientEmail : emailRequest.getToEmailList()) {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, Boolean.TRUE);
                helper.setFrom(email);
                helper.setTo(recipientEmail);
                helper.setSubject(emailRequest.getSubject());
                helper.setText(htmlContent, Boolean.TRUE);
                // send the email
                javaMailSender.send(mimeMessage);
                ++count;

                if(count % 40 == 0) {
                    Thread.sleep(2000);
                }
            }

            LOGGER.info(Constants.NOTIFICATION_SUCCESSFUL_MESSAGE);
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new EmailServiceException(String.format(Constants.EXCEPTION_MESSAGE, "sendTemplateEmail()", e.getMessage()),
                    ProjectUtil.getCurrentTimeStamp());
        }
    }
}
