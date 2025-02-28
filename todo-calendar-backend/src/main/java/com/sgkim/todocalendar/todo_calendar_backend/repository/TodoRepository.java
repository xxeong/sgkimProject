package com.sgkim.todocalendar.todo_calendar_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgkim.todocalendar.todo_calendar_backend.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
