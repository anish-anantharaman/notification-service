# Notification Service


**Author:** Anish  
**Date:** 2025-04-20


## Overview

This is a microservice developed for sending notifications. It currently supports notifications
through Email, Microsoft Teams (channel notification with webhooks) and Firebase (app notification).


## Base URL

```
http://localhost:8080/api
```

## Endpoints

### Send email without template

```
POST /v1/email
```

**Description:** Sends a simple email with some subject and content

**Request Body:**

```json
{
  "toEmailList": ["email1@gmail.com", "email2@gmail.com"],
  "subject": "Request approved !",
  "message": "Your request for leave has been approved."
}
```

**Success Response:**
```json
{
	"data": true,
	"message": "success",
	"status": 200
}
```

### Send email with template

```
POST /v1/template-email
```

**Description:** Sends email with a subject and template content

**Request Body:**

```json
{
  "toEmailList": ["email1@gmail.com", "email2@gmail.com"],
  "subject": "Your Registration Has Been Approved",
  "message": "Congratulations ! Your registration for the event is successful",
  "buttonText": "View Now",
  "buttonUrl": "https://www.google.com"
}
```

### Send Microsoft Teams channel notification

```
POST /v1/teams/message
```

**Description:** Sends Microsoft Teams Channel notification

**Request Body:**

```json
{
	"title": "Your Registration Has Been Approved",
	"heading": "Congratulations ! Your registration for the event is successful",
	"content": "We welcome your presence at the event.",
	"buttonText": "View",
	"buttonUrl": "https://www.google.com"
}
```

### Send Firebase topic notification

```
POST /v1/topic-notification
```

**Description:** Sends a Firebase app notification to a topic

**Request Body:**

```json
{
	"topic" : "event",
	"title": "New Event Added",
	"content": "New Event on Java Latest Trends Discussion"
}
```

### Send Firebase token notification

```
POST /v1/token-notification
```

**Description:**  Send Firebase app notification with a list of tokens

**Request Body:**

```json
{
	"deviceTokenList": ["fEKqxrJbTZS_Ix5E_JSF_J:AW8KcdBoyBJqruvmnjJorlqdp5S0dhCZgAM", "gjhgjhgjfEKqxrJbTZS_Ix5E_Jdhdg:KcdBoyBJqruvmnjJdjhjwhvjh0dhCZgAM"],
	"title": "Leave Approved",
	"content": "Your request for leave has been approved"
}
```

## Responses

**Success Response:**

```json
{
	"data": true,
	"message": "success",
	"status": 200
}
```

**Error Response - Missing required field in request body:**

```json
{
	"message": "failed",
	"status": 400
}
```

**Error Response - Error from server end:**

```json
{
	"message": "failed",
	"status": 500
}
```

## Endpoints Summary

| Method | Endpoint                   | Description                               |
|--------|----------------------------|-------------------------------------------|
| POST   | /v1/email                  | Send simple email                         |
| POST   | /v1/template-email         | Send email with template content          |
| POST   | /v1/teams/message          | Send Microsoft Teams channel notification |
| POST   | /v1/topic-notification     | Send Firebase topic notification          |
| POST   | /v1/token-notification     | Send Firebase token notification          |


## Response Codes

| Status Code | Meaning               | Description                             |
|-------------|-----------------------|-----------------------------------------|
| 200         | OK                    | Request was successful                  |
| 400         | Bad Request           | Invalid input, missing required fields  |
| 500         | Internal Server Error | Something went wrong on the server side |
