package com.service.NotificationService.model;

import lombok.Data;

@Data
public class TeamsRequest {
    String title;
    String heading;
    String content;
    String buttonText;
    String buttonUrl;
}