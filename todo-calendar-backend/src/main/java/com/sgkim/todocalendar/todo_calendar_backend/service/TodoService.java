package com.sgkim.todocalendar.todo_calendar_backend.service;

import com.sgkim.todocalendar.todo_calendar_backend.model.Todo;
import com.sgkim.todocalendar.todo_calendar_backend.repository.TodoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
}