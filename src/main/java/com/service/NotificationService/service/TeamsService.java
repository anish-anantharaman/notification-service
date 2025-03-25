package com.service.NotificationService.service;

import com.service.NotificationService.model.TeamsRequest;

public interface TeamsService {
    boolean sendTeamsWebhookNotification(TeamsRequest teamsRequest);
}
