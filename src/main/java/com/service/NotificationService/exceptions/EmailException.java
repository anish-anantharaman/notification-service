package com.service.NotificationService.exceptions;

import com.service.NotificationService.util.Constants;

public class EmailException extends RuntimeException {
    public EmailException(String message, String timeStamp) {
        super("[" + timeStamp + "]" + Constants.EMPTY_STRING + message);
    }
}

