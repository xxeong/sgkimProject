package com.sgkim.todocalendar.todo_calendar_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgkim.todocalendar.todo_calendar_backend.model.Todo;
import com.sgkim.todocalendar.todo_calendar_backend.service.TodoService;

@RestController
@RequestMapping("/api/todo")
public class TodoBackendController {

    private final TodoService todoService;

    public TodoBackendController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/List")
    public ResponseEntity<List<Todo>> getTodos() {
        List<Todo> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }
    
}
