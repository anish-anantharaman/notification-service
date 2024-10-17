package com.service.NotificationService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;
@Service
public class TeamsService {

    private final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper mapper;

    @Value("${teams.webhook.url}")
    private String webhookUrl;

    public boolean sendTeamsWebhookNotification(String title, String heading, String content, String buttonText, String buttonUrl) {
        try {
              // build json request
              String jsonBody = createJsonBody(title, heading, content, buttonText, buttonUrl);

              HttpHeaders headers = new HttpHeaders();
              headers.setContentType(MediaType.APPLICATION_JSON);


              // create request entity and send request
              HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

              ResponseEntity<String> response = restTemplate.exchange(webhookUrl, HttpMethod.POST, requestEntity, String.class);

              LOGGER.info("Value is " + response.getStatusCode());
              if (response.getStatusCode() == HttpStatus.OK) {
                  LOGGER.info("Notification sent successfully");
                  return Boolean.TRUE;
              }
              LOGGER.severe("Error in teams messaging server");
              return Boolean.FALSE;
        } catch (Exception e) {
            LOGGER.severe("Error while sending message in sendTeamsWebhookNotification method : " + e.getMessage());
            return Boolean.FALSE;
        }
    }

    private String createJsonBody(String title, String heading, String content, String buttonText, String buttonUrl) {
        ObjectNode jsonBody = mapper.createObjectNode();
        jsonBody.put("summary", title);
        jsonBody.put("themeColor", "0076D7");
        jsonBody.put("title", title);

        // Create sections array
        ArrayNode sections = mapper.createArrayNode();
        ObjectNode section = mapper.createObjectNode();
        section.put("activityTitle", heading);
        section.put("text", content);
        sections.add(section);
        jsonBody.set("sections", sections);

        // Create potentialAction array
        ArrayNode potentialAction = mapper.createArrayNode();
        ObjectNode action = mapper.createObjectNode();
        action.put("@type", "OpenUri");
        action.put("name", buttonText);

        ArrayNode targets = mapper.createArrayNode();
        ObjectNode target = mapper.createObjectNode();
        target.put("os", "default");
        target.put("uri", buttonUrl);
        targets.add(target);
        action.set("targets", targets);

        potentialAction.add(action);
        jsonBody.set("potentialAction", potentialAction);

        return jsonBody.toString();
    }
}
