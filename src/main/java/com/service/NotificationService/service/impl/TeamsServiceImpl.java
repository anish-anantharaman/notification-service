package com.service.NotificationService.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.service.NotificationService.exception.TeamsServiceException;
import com.service.NotificationService.service.TeamsService;

import com.service.NotificationService.model.TeamsRequest;
import com.service.NotificationService.util.Constants;
import com.service.NotificationService.util.ProjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TeamsServiceImpl implements TeamsService {

    private final Logger LOGGER = LoggerFactory.getLogger(TeamsServiceImpl.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String webhookUrl;

    @Autowired
    public TeamsServiceImpl(RestTemplate restTemplate, ObjectMapper mapper,
                            @Value("${teams.webhook.url}") String webhookUrl) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.webhookUrl = webhookUrl;
    }

    @Override
    public boolean sendTeamsWebhookNotification(TeamsRequest teamsRequest) {
        try {
              // build json request
              String jsonBody = createJsonBody(teamsRequest);

              HttpHeaders headers = new HttpHeaders();
              headers.setContentType(MediaType.APPLICATION_JSON);


              // create request entity and send request
              HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

              ResponseEntity<String> response = restTemplate.exchange(webhookUrl,
                      HttpMethod.POST, requestEntity, String.class);

              if (response.getStatusCode() == HttpStatus.OK) {
                  LOGGER.info(Constants.NOTIFICATION_SUCCESSFUL_MESSAGE);
                  return Boolean.TRUE;
              }
              return Boolean.FALSE;
        } catch (Exception e) {
            throw new TeamsServiceException(String.format(Constants.EXCEPTION_MESSAGE, "sendTeamsWebhookNotification()", e.getMessage()),
                    ProjectUtil.getCurrentTimeStamp());
        }
    }

    private String createJsonBody(TeamsRequest teamsRequest) {
        ObjectNode jsonBody = mapper.createObjectNode();
        jsonBody.put(Constants.SUMMARY, teamsRequest.getTitle());
        jsonBody.put(Constants.THEME_COLOR, "0076D7");
        jsonBody.put(Constants.TITLE, teamsRequest.getTitle());

        // create sections array
        ArrayNode sections = mapper.createArrayNode();
        ObjectNode section = mapper.createObjectNode();
        section.put(Constants.ACTIVITY_TITLE, teamsRequest.getHeading());
        section.put(Constants.TEXT, teamsRequest.getContent());
        sections.add(section);
        jsonBody.set(Constants.SECTIONS, sections);

        // create potentialAction array
        ArrayNode potentialAction = mapper.createArrayNode();
        ObjectNode action = mapper.createObjectNode();
        action.put("@type", "OpenUri");
        action.put(Constants.NAME, teamsRequest.getButtonText());

        ArrayNode targets = mapper.createArrayNode();
        ObjectNode target = mapper.createObjectNode();
        target.put("os", Constants.DEFAULT);
        target.put(Constants.URI, teamsRequest.getButtonUrl());
        targets.add(target);
        action.set(Constants.TARGETS, targets);

        potentialAction.add(action);
        jsonBody.set(Constants.POTENTIAL_ACTION, potentialAction);

        return jsonBody.toString();
    }
}
