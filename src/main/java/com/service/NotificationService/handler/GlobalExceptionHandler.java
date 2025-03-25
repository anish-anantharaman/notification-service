package com.service.NotificationService.handler;

import com.service.NotificationService.util.Constants;
import com.service.NotificationService.util.ProjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

   private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        LOGGER.warn(Constants.GLOBAL_EXCEPTION_MESSAGE + Constants.EMPTY_STRING + e.getMessage());
        return ProjectUtil.failure(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(Exception e) {
        LOGGER.warn(Constants.GLOBAL_EXCEPTION_MESSAGE + Constants.EMPTY_STRING + e.getMessage());
        return ProjectUtil.failure(HttpStatus.BAD_REQUEST);
    }
}
