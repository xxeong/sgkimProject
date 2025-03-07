package com.sgkim.todocalendar.todo_calendar_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

import java.util.Map;

@Service
public class AzureAuthService {
    @Value("${azure.client.id}")  // ✅ Spring 환경 변수로 값 가져오기
    private String clientId;

    @Value("${azure.client.secret}")
    private String clientSecret;

    @Value("${azure.client.tenant}")
    private String tenantId;

    private String tokenUrl;

    @PostConstruct  // ✅ 생성자 실행 후 초기화
    public void init() {
        this.tokenUrl = "https://login.microsoftonline.com/" + tenantId + "/oauth2/v2.0/token";
    }

    public String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 🔹 `MultiValueMap`을 사용하여 올바른 데이터 형식으로 변환
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("scope", "https://graph.microsoft.com/.default");
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to get Access Token: " + response.getBody());
        }

        return (String) response.getBody().get("access_token");
    }
}
