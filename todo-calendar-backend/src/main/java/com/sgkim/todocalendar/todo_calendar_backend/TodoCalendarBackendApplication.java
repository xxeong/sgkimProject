package com.sgkim.todocalendar.todo_calendar_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class TodoCalendarBackendApplication {

	public static void main(String[] args) {
		// .env 파일 로드
		Dotenv dotenv = Dotenv.configure()
				.directory(System.getProperty("user.dir")+"/todo-calendar-backend") // 현재 작업 디렉토리 사용
				.filename(".env") // 기본 파일명 `.env`
				//.ignoreIfMissing() // 파일이 없으면 무시 (오류 방지)
				.load();
		System.setProperty("AZURE_CLIENT_SECRET", dotenv.get("AZURE_CLIENT_SECRET"));

		SpringApplication.run(TodoCalendarBackendApplication.class, args);
	}

}
