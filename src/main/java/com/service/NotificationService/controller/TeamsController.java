package com.service.NotificationService.controller;

import com.service.NotificationService.model.TeamsRequest;
import com.service.NotificationService.service.TeamsService;
import com.service.NotificationService.util.ProjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1")
public class TeamsController {

    @Autowired
    private final TeamsService teamsService;
    private static final Logger LOGGER = Logger.getLogger(TeamsController.class.getName());

    public TeamsController (TeamsService teamsService) { this.teamsService = teamsService; }

    @PostMapping("/teams/message")
    public ResponseEntity<Object> sendTeamsWebhookNotification(@RequestBody TeamsRequest teamsRequest) {
        try {
            boolean response = teamsService.sendTeamsWebhookNotification(teamsRequest.getTitle(), teamsRequest.getHeading(),
                    teamsRequest.getContent(), teamsRequest.getButtonText(), teamsRequest.getButtonUrl());

            if(response) {
                LOGGER.info("Message sent successfully");
                return ProjectUtil.success(Boolean.TRUE);
            }

        } catch(Exception e) {
            LOGGER.severe("Error while trying to send email :" + e.getMessage());
            return ProjectUtil.failure(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ProjectUtil.success(HttpStatus.BAD_REQUEST);
    }

}
