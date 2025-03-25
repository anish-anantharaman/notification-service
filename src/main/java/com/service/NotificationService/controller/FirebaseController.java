package com.service.NotificationService.controller;

import com.service.NotificationService.model.FirebaseRequest;
import com.service.NotificationService.service.FirebaseService;
import com.service.NotificationService.util.ProjectUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1")
public class FirebaseController {

    private final FirebaseService firebaseService;

    @Autowired
    public FirebaseController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    /**
     * this api is used to send bulk firebase notifications for
     * specific devices with their device token
     * @param firebaseRequest contains the notification content and the app device tokens
     * @return success or failure response for the notification sent
     */
    @PostMapping(path = "/token-notification", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sendNotificationByDeviceToken(@RequestBody FirebaseRequest firebaseRequest) {
        boolean response = firebaseService.sendNotificationByDeviceToken(firebaseRequest);
        return ProjectUtil.success(response);
    }

    /**
     * this api is used to send bulk firebase notifications for
     * all devices which are subscribed to a specific topic
     * @param firebaseRequest contains the notification content and the topic value
     * @return success or failure response for the notification sent
     */
    @PostMapping(path = "/topic-notification", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sendNotificationByTopic(@RequestBody FirebaseRequest firebaseRequest) {
        boolean response = firebaseService.sendNotificationByTopic(firebaseRequest);
        return ProjectUtil.success(response);
    }
}
