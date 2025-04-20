package com.service.NotificationService.exception;

import com.service.NotificationService.util.Constants;

public class EmailServiceException extends RuntimeException {
    public EmailServiceException(String message, String timeStamp) {
        super("[" + timeStamp + "]" + Constants.EMPTY_STRING + message);
    }
}

