package com.sgkim.todocalendar.todo_calendar_backend.service;

import com.sgkim.todocalendar.todo_calendar_backend.model.dto.TodoDescriptionDto;
import com.sgkim.todocalendar.todo_calendar_backend.model.dto.TodoDto;
import com.sgkim.todocalendar.todo_calendar_backend.model.entity.TodoDescriptionEntity;
import com.sgkim.todocalendar.todo_calendar_backend.model.entity.TodoEntity;
import com.sgkim.todocalendar.todo_calendar_backend.repository.TodoDescriptionRepository;
import com.sgkim.todocalendar.todo_calendar_backend.repository.TodoRepository;

import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoDescriptionRepository todoDescriptionRepository;
    private final ModelMapper modelMapper;

    public TodoService(TodoRepository todoRepository, ModelMapper modelMapper,
            TodoDescriptionRepository todoDescriptionRepository) {
        this.todoRepository = todoRepository;
        this.todoDescriptionRepository = todoDescriptionRepository;
        this.modelMapper = modelMapper;
    }

    // ✅ 특정 ID의 할 일 조회
    public TodoDto getTodoById(Long id) {
        TodoEntity entity = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("할 일을 찾을 수 없습니다. ID = " + id));
        return modelMapper.map(entity, TodoDto.class);
    }

    // ✅ 전체 할 일 조회 (정렬 옵션 추가)
    public List<TodoDto> getAllTodos(String order) {
        List<TodoEntity> todos = (order != null && order.equals("asc")) 
                ? todoRepository.findAllByOrderByDueDateAsc()
                : todoRepository.findAllByOrderByDueDateDesc();

        return todos.stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    // ✅ 할 일 추가
    public TodoDto saveTodo(TodoDto todo) {
        TodoEntity entity = modelMapper.map(todo, TodoEntity.class);
        TodoEntity savedEntity = todoRepository.save(entity);
        return modelMapper.map(savedEntity, TodoDto.class);
    }

    // ✅ 전체 수정(PUT)
    public TodoDto putTodo(Long id, TodoDto tododto) {
        TodoEntity todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("할 일을 찾을 수 없습니다. ID = " + id));

        todo.setTitle(tododto.getTitle());
        todo.setDueDate(tododto.getDueDate());
        todo.setStatus(tododto.getStatus());

        TodoEntity updatedEntity = todoRepository.save(todo);
        return modelMapper.map(updatedEntity, TodoDto.class);
    }

    // ✅ 부분 수정(PATCH)
    public TodoDto patchTodo(Long id, TodoDto updates) {
        TodoEntity todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("할 일을 찾을 수 없습니다. ID = " + id));

        if (updates.getStatus() != null) {
            todo.setStatus(updates.getStatus());
        }

        TodoEntity updatedEntity = todoRepository.save(todo);
        return modelMapper.map(updatedEntity, TodoDto.class);
    }

    // ✅ 할 일 삭제
    public void deleteTodo(Long id) {
        TodoEntity entity = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("할 일을 찾을 수 없습니다. ID = " + id));

        todoRepository.delete(entity);
    }

    public List<TodoDescriptionDto> getTodoDescriptions(){
        List<TodoDescriptionEntity> descriptions = todoDescriptionRepository.findAll();
        return descriptions.stream()
        .map(desc -> modelMapper.map(desc, TodoDescriptionDto.class))
        .collect(Collectors.toList());
    }

    // ✅ 특정 Todo에 대한 설명 목록 조회
    public List<TodoDescriptionDto> getTodoDescriptionsByTodoId(Long todoId) {
        List<TodoDescriptionEntity> descriptions = todoDescriptionRepository.findByTodoId(todoId);

        return descriptions.stream()
                .map(desc -> modelMapper.map(desc, TodoDescriptionDto.class))
                .collect(Collectors.toList());
    }

    // ✅ 설명 추가
    public TodoDescriptionDto saveTodoDescriptions(Long todoId, TodoDescriptionDto todoDescription) {
        TodoEntity todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new EntityNotFoundException("Todo가 존재하지 않습니다. ID = " + todoId));

        TodoDescriptionEntity entity = modelMapper.map(todoDescription, TodoDescriptionEntity.class);
        entity.setTodo(todo);

        TodoDescriptionEntity savedEntity = todoDescriptionRepository.save(entity);
        return modelMapper.map(savedEntity, TodoDescriptionDto.class);
    }

    // ✅ 설명 수정
    public TodoDescriptionDto updateTodoDescription(Long descriptionId, TodoDescriptionDto descriptionDto) {
        TodoDescriptionEntity description = todoDescriptionRepository.findById(descriptionId)
                .orElseThrow(() -> new EntityNotFoundException("설명을 찾을 수 없습니다. ID = " + descriptionId));

        description.setDescription(descriptionDto.getDescription());

        TodoDescriptionEntity updatedEntity = todoDescriptionRepository.save(description);
        return modelMapper.map(updatedEntity, TodoDescriptionDto.class);
    }

    // ✅ 설명 삭제
    public void deleteTodoDescriptions(Long descriptionId) {
        if (!todoDescriptionRepository.existsById(descriptionId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "설명을 찾을 수 없습니다. ID = " + descriptionId);
        }
        todoDescriptionRepository.deleteById(descriptionId);
    }

}