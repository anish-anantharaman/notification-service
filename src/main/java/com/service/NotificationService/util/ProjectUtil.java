package com.service.NotificationService.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ProjectUtil {

    /**
     * this is the utility method for handling success response
     * @param data contains the data
     * @return success response builder with data
     */
    public static ResponseEntity<Object> success(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put(Constants.STATUS, HttpStatus.OK.value());
        response.put(Constants.MESSAGE, Constants.SUCCESS);
        response.put(Constants.DATA, data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * this is the utility method for handling failure response
     * @param status response status code
     * @return failure response based on status received
     */
    public static ResponseEntity<Object> failure(HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put(Constants.STATUS, status.value());
        response.put(Constants.MESSAGE, Constants.FAILED);
        return new ResponseEntity<>(response, status);
    }

    /**
     * this is a utility method which returns the current timestamp
     * @return current timestamp
     */
    public static String getCurrentTimeStamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
