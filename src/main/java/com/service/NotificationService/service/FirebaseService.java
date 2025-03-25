package com.service.NotificationService.service;

import com.service.NotificationService.model.FirebaseRequest;

public interface FirebaseService {
    boolean sendNotificationByDeviceToken(FirebaseRequest firebaseRequest);
    boolean sendNotificationByTopic(FirebaseRequest firebaseRequest);
}
