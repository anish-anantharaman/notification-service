package com.service.NotificationService.controller;

import com.service.NotificationService.model.EmailRequest;
import com.service.NotificationService.service.EmailService;
import com.service.NotificationService.util.Constants;
import com.service.NotificationService.util.ProjectUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * this api sends simple email notification with some subject and content
     * @param emailRequest contains the email content and receiver's email id
     * @return success or failure response on the email sent
     */
    @PostMapping(path = "/email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sendEmail(@RequestBody EmailRequest emailRequest) {
        boolean response = emailService.sendEmail(emailRequest);
        return ProjectUtil.success(response);
    }

    /**
     * this api sends an email with a defined template with some subject and content
     * @param emailRequest contains the email content and the receiver's email id
     * @return success or failure response on the email sent
     */
    @PostMapping(path = "/template-email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sendTemplateEmail(@RequestBody EmailRequest emailRequest) {
        boolean response = emailService.sendTemplateEmail(emailRequest);
        return ProjectUtil.success(response);
    }
}
