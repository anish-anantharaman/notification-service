package com.service.NotificationService.exceptions;

import com.service.NotificationService.util.Constants;

public class TeamsServiceException extends RuntimeException {
    public TeamsServiceException(String message, String timeStamp) {
        super("[" + timeStamp + "]" + Constants.EMPTY_STRING + message);
    }
}
