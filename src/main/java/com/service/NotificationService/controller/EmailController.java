package com.service.NotificationService.controller;

import com.service.NotificationService.model.EmailRequest;
import com.service.NotificationService.service.EmailService;
import com.service.NotificationService.util.ProjectUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1")
public class EmailController {

    @Autowired
    private final EmailService emailService;

    private static final Logger LOGGER = Logger.getLogger(EmailController.class.getName());

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-email")
    public ResponseEntity<Object> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            boolean response = emailService.sendEmail(emailRequest.getToEmail(), emailRequest.getSubject(), emailRequest.getMessage());
            if(response) {
                LOGGER.info("Email sent successfully");
                return ProjectUtil.success(Boolean.TRUE);
            }
        } catch(Exception e) {
            LOGGER.severe("Error while trying to send email :" + e.getMessage());
            return ProjectUtil.failure(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ProjectUtil.failure(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/send-template-email")
    public ResponseEntity<Object> sendTemplateEmail(@RequestBody EmailRequest emailRequest) {
        try {
            boolean response = emailService.sendTemplateEmail(emailRequest.getToEmail(),emailRequest.getSubject(),
                    emailRequest.getMessage(), emailRequest.getButtonUrl());
            if(response) {
                LOGGER.info("Email sent successfully");
                return ProjectUtil.success(Boolean.TRUE);
            }
        } catch(Exception e) {
            LOGGER.severe("Error while trying to send email : " + e.getMessage());
            return ProjectUtil.success(Boolean.TRUE);
        }
        return ProjectUtil.failure(HttpStatus.BAD_REQUEST);
    }
}
