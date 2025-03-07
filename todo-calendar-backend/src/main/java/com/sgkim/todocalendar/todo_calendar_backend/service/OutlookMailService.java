package com.sgkim.todocalendar.todo_calendar_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OutlookMailService {
    private final AzureAuthService authService;

    @Value("${azure.mail.sender}")
    private String senderEmail;

    public OutlookMailService(AzureAuthService authService) {
        this.authService = authService;
    }

    public void sendEmail(String to, String subject, String content) {
        String accessToken = authService.getAccessToken();
        String url = "https://graph.microsoft.com/v1.0/users/" + senderEmail + "/sendMail";

        String jsonPayload = "{ \"message\": { " +
                "\"subject\": \"" + subject + "\", " +
                "\"body\": { \"contentType\": \"HTML\", \"content\": \"" + content + "\" }, " +
                "\"toRecipients\": [ { \"emailAddress\": { \"address\": \"" + to + "\" } } ] } }";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() != HttpStatus.ACCEPTED && response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("이메일 전송 실패: " + response.getBody());
        }
    }
}
