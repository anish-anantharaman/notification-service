package com.service.NotificationService.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ProjectUtil {

    // Success response method
    public static ResponseEntity<Object> success(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "success");
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Failure response method
    public static ResponseEntity<Object> failure(HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", "failed");
        return new ResponseEntity<>(response, status);
    }
}
