package com.service.NotificationService.model;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class FirebaseRequest {

    private List<String> deviceTokenList;

    private String topic;

    @NonNull
    private String title;

    @NonNull
    private String content;
}
