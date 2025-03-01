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

    public Todo getTodoById(Long id){
        return todoRepository.findById(id).orElseThrow(()-> new RuntimeException("값이 존재하지 않습니다. Id = "+id));
    }
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo saveTodo(Todo todo){
        return todoRepository.save(todo);
    }
}