package com.sgkim.todocalendar.todo_calendar_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.sgkim.todocalendar.todo_calendar_backend.converter.EnumConverter;

@Entity
@Table(name = "todo")
@Getter @Setter
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime dueDate;

    @Convert(converter = EnumConverter.class) // ✅ ENUM 변환기 적용
    private Status status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 상태 ENUM
    public enum Status {
        PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    }

    // 기본 생성자
    public Todo() {}
}