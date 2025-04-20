package com.service.NotificationService.model;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class EmailRequest {

    @NonNull
    private List<String> toEmailList;

    @NonNull
    private String subject;

    @NonNull
    private String message;

    private String buttonText;

    private String buttonUrl;
}
