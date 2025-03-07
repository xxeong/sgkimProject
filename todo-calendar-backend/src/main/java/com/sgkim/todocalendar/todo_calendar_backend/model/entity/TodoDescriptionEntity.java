package com.sgkim.todocalendar.todo_calendar_backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "todo_descriptions")
@Getter @Setter
public class TodoDescriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description; // ✅ 설명 내용

    @ManyToOne
    @JoinColumn(name = "todo_id", nullable = false)
    private TodoEntity todo;
}