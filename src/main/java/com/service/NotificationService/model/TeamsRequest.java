package com.service.NotificationService.model;

import lombok.Data;
import lombok.NonNull;


@Data
public class TeamsRequest {

    @NonNull
    private String title;

    @NonNull
    private String heading;

    @NonNull
    private String content;

    @NonNull
    private String buttonText;

    @NonNull
    private String buttonUrl;
}
