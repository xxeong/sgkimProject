package com.sgkim.todocalendar.todo_calendar_backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import java.util.List;

@Entity
@Table(name = "todo")
@Getter @Setter
public class TodoEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    // @Column(columnDefinition = "TEXT")
    // private String description;

    // ✅ 1:N 관계 설정 (하나의 Todo가 여러 개의 설명을 가짐)
    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodoDescriptionEntity> descriptions;

    @Column(name = "due_date", columnDefinition = "DATE")  // ✅ `snake_case` 매핑
    private LocalDate dueDate;

    @Column(name = "status", length = 10, nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'N'")  // ✅ `VARCHAR(10)` 유지
    private String status = "N";

    // 기본 생성자
    public TodoEntity() {}
}