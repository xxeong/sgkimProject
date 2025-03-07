package com.sgkim.todocalendar.todo_calendar_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgkim.todocalendar.todo_calendar_backend.model.entity.TodoDescriptionEntity;

public interface TodoDescriptionRepository extends JpaRepository<TodoDescriptionEntity, Long> {
    
    List<TodoDescriptionEntity> findByTodoId(Long todoId);
}
