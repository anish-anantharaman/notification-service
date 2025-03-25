package com.service.NotificationService.service.impl;

import com.google.firebase.messaging.*;
import com.service.NotificationService.exceptions.FirebaseServiceException;
import com.service.NotificationService.model.FirebaseRequest;
import com.service.NotificationService.service.FirebaseService;
import com.service.NotificationService.util.Constants;
import com.service.NotificationService.util.ProjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class FirebaseServiceImpl implements FirebaseService {
    private final Logger LOGGER = LoggerFactory.getLogger(FirebaseServiceImpl.class);

    @Override
    public boolean sendNotificationByDeviceToken(FirebaseRequest firebaseRequest) {

            if(firebaseRequest.getDeviceTokenList() == null || firebaseRequest.getDeviceTokenList().isEmpty()) {
                throw new FirebaseServiceException("Device token list is null or empty",
                        ProjectUtil.getCurrentTimeStamp());
            }

            MulticastMessage multicastMessage = MulticastMessage.builder()
                    .addAllTokens(firebaseRequest.getDeviceTokenList())
                    .putData(Constants.TITLE, firebaseRequest.getTitle())
                    .putData(Constants.BODY, firebaseRequest.getContent())
                    .build();
        try {
            FirebaseMessaging.getInstance().sendEachForMulticast(multicastMessage);
            LOGGER.info(Constants.NOTIFICATION_SUCCESSFUL_MESSAGE);
            return Boolean.TRUE;
        } catch (FirebaseMessagingException e) {
            throw new FirebaseServiceException(String.format(Constants.EXCEPTION_MESSAGE, "sendNotificationByDeviceToken()", e.getMessage()),
                    ProjectUtil.getCurrentTimeStamp());
        }
    }

    @Override
    public boolean sendNotificationByTopic(FirebaseRequest firebaseRequest) {

        if(firebaseRequest.getTopic() == null || firebaseRequest.getTopic().isEmpty()) {
            throw new FirebaseServiceException("Firebase Topic is null or empty value",
                    ProjectUtil.getCurrentTimeStamp());
        }

        Message message = Message.builder()
                .putData(Constants.TITLE, firebaseRequest.getTitle())
                .putData(Constants.BODY, firebaseRequest.getContent())
                .setTopic(firebaseRequest.getTopic())
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
            LOGGER.info(Constants.NOTIFICATION_SUCCESSFUL_MESSAGE);
            return Boolean.TRUE;
        } catch (FirebaseMessagingException e) {
            throw new FirebaseServiceException(String.format(Constants.EXCEPTION_MESSAGE, "sendNotificationByTopic()", e.getMessage()),
                    ProjectUtil.getCurrentTimeStamp());
        }
    }
}
