package com.service.NotificationService.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class EmailRequest {

    @NonNull
    private String toEmail;

    @NonNull
    private String subject;

    @NonNull
    private String message;

    private String buttonText;

    private String buttonUrl;
}
