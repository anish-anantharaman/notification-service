package com.service.NotificationService.controller;

import com.service.NotificationService.model.TeamsRequest;
import com.service.NotificationService.service.TeamsService;
import com.service.NotificationService.util.Constants;
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
public class TeamsController {

    private final TeamsService teamsService;

    @Autowired
    public TeamsController (TeamsService teamsService) {
        this.teamsService = teamsService;
    }

    /**
     * this api sends channel notification on Microsoft Teams
     * @param teamsRequest contains the notification content to be sent
     * @return success or failure response of the message sent
     */
    @PostMapping(path = "/teams/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sendTeamsWebhookNotification(@RequestBody TeamsRequest teamsRequest) {
        System.out.println(teamsRequest);
        boolean response = teamsService.sendTeamsWebhookNotification(teamsRequest);
        return ProjectUtil.success(response);
    }
}
