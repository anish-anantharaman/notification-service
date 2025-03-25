package com.service.NotificationService.service;

import com.service.NotificationService.model.EmailRequest;

public interface EmailService {
    boolean sendEmail(EmailRequest emailRequest);
    boolean sendTemplateEmail(EmailRequest emailRequest);
}
