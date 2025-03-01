package com.sgkim.todocalendar.todo_calendar_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.sgkim.todocalendar.todo_calendar_backend.model.entity.BaseEntity;

@Entity
@Table(name = "todo")
@Getter @Setter
public class Todo extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "dueDate", columnDefinition = "TEXT")
    private LocalDateTime dueDate;

    //@Convert(converter = EnumConverter.class) // ✅ ENUM 변환기 적용
    private String status;

    // 기본 생성자
    public Todo() {}
}