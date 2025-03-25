package com.service.NotificationService.exceptions;

import com.service.NotificationService.util.Constants;

public class FirebaseServiceException extends RuntimeException {
    public FirebaseServiceException(String message, String timestamp) {
        super("[" + timestamp + "]" + Constants.EMPTY_STRING + message);
    }
}
