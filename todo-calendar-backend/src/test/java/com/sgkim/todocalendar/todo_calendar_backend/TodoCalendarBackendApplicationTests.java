package com.sgkim.todocalendar.todo_calendar_backend;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TodoCalendarBackendApplicationTests {

	@Value("${azure.client.secret}")
    private String azureClientSecret;

    @BeforeAll
    static void setup() {
        // ✅ 테스트 실행 전에 환경 변수 강제 로드
        TodoCalendarBackendApplication.loadEnv();
    }

    @Test
    public void testAzureClientSecretLoaded() {
        assertNotNull(azureClientSecret, "❌ AZURE_CLIENT_SECRET 값이 로드되지 않았습니다!");
        System.out.println("✅ AZURE_CLIENT_SECRET: " + azureClientSecret);
    }

}
