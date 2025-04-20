package com.service.NotificationService.conf;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.service.NotificationService.exception.FirebaseServiceException;
import com.service.NotificationService.util.Constants;
import com.service.NotificationService.util.ProjectUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@Component
public class FirebaseInitializer implements CommandLineRunner {
    private final Logger LOGGER = Logger.getLogger(FirebaseInitializer.class.getName());

    @Override
    public void run(String[] args) {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                InputStream serviceAccount = new ClassPathResource("firebase.json").getInputStream();
                FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
                FirebaseApp.initializeApp(firebaseOptions);
                LOGGER.info("Firebase initialized successfully");
            }
        } catch (IOException e) {
            throw new FirebaseServiceException(String.format(Constants.EXCEPTION_MESSAGE, "FirebaseInitializer run()", e.getMessage()),
                    ProjectUtil.getCurrentTimeStamp());
        }
    }
}
