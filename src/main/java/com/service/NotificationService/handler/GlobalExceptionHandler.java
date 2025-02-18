package com.service.NotificationService.handler;

import com.service.NotificationService.util.Constants;
import com.service.NotificationService.util.ProjectUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        LOGGER.severe(Constants.GLOBAL_EXCEPTION_MESSAGE + Constants.EMPTY_STRING + e.getMessage());
        return ProjectUtil.failure(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(Exception e) {
        LOGGER.severe(Constants.GLOBAL_EXCEPTION_MESSAGE + Constants.EMPTY_STRING + e.getMessage());
        return ProjectUtil.failure(HttpStatus.BAD_REQUEST);
    }
}
