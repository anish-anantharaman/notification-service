package com.service.NotificationService.controller;

import com.service.NotificationService.model.EmailRequest;
import com.service.NotificationService.service.EmailService;
import com.service.NotificationService.util.Constants;
import com.service.NotificationService.util.ProjectUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1")
public class EmailController {

    private final EmailService emailService;

    private static final Logger LOGGER = Logger.getLogger(EmailController.class.getName());

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-email")
    public ResponseEntity<Object> sendEmail(@RequestBody EmailRequest emailRequest) {
        boolean response = emailService.sendEmail(emailRequest);
        LOGGER.info(Constants.NOTIFICATION_SUCCESSFUL_MESSAGE);
        return ProjectUtil.success(response);
    }

    @PostMapping("/send-template-email")
    public ResponseEntity<Object> sendTemplateEmail(@RequestBody EmailRequest emailRequest) {
        boolean response = emailService.sendTemplateEmail(emailRequest);
        LOGGER.info(Constants.NOTIFICATION_SUCCESSFUL_MESSAGE);
        return ProjectUtil.success(response);
    }
}
