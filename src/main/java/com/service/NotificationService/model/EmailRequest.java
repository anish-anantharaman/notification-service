package com.service.NotificationService.model;

import lombok.Data;

@Data
public class EmailRequest {
    private String toEmail;
    private String subject;
    private String message;
    private String buttonUrl;
}
