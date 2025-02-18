package com.service.NotificationService.model;

import lombok.Data;
import lombok.NonNull;


@Data
public class TeamsRequest {

    @NonNull
    String title;

    @NonNull
    String heading;

    @NonNull
    String content;

    @NonNull
    String buttonText;

    @NonNull
    String buttonUrl;
}