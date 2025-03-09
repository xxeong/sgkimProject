package com.sgkim.todocalendar.todo_calendar_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class TodoCalendarBackendApplication {

	public static void main(String[] args) {
		// ✅ 환경 변수를 강제 로드하여 `SpringApplication.run()` 이전에 적용
		loadEnv();

		SpringApplication.run(TodoCalendarBackendApplication.class, args);
	}

	// ✅ 테스트 환경에서도 실행될 수 있도록 `.env` 로드 메서드 분리
	public static void loadEnv() {
		Dotenv dotenv = Dotenv.configure()
				.directory(System.getProperty("user.dir")) // 현재 프로젝트 루트 사용
				.filename(".env")
				.ignoreIfMissing()
				.load();

		// ✅ 시스템 환경 변수로 등록하여 Spring이 인식할 수 있도록 함
		System.setProperty("AZURE_CLIENT_SECRET", dotenv.get("AZURE_CLIENT_SECRET", "default-secret"));

		System.out.println("✅ AZURE_CLIENT_SECRET 로드됨: " + System.getProperty("AZURE_CLIENT_SECRET"));
	}

}
