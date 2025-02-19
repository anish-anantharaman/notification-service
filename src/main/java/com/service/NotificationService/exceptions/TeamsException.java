package com.service.NotificationService.exceptions;

import com.service.NotificationService.util.Constants;

public class TeamsException extends RuntimeException {
    public TeamsException(String message, String timeStamp) {
        super("[" + timeStamp + "]" + Constants.EMPTY_STRING + message);
    }
}
