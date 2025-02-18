package com.service.NotificationService.controller;

import com.service.NotificationService.model.TeamsRequest;
import com.service.NotificationService.service.TeamsService;
import com.service.NotificationService.util.Constants;
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
        boolean response = teamsService.sendTeamsWebhookNotification(teamsRequest);
        LOGGER.info(Constants.NOTIFICATION_SUCCESSFUL_MESSAGE);
        return ProjectUtil.success(response);
    }
}
