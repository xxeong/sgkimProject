package com.sgkim.todocalendar.todo_calendar_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    // 1. 특정 ID 조회
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    // 2. 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<Todo>> getTodos() {
        List<Todo> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    // 3. todo 생성(추가)-create
    @PostMapping("/add")
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo){
        return ResponseEntity.ok(todoService.saveTodo(todo));
    }

    
}
