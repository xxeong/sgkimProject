package com.sgkim.todocalendar.todo_calendar_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sgkim.todocalendar.todo_calendar_backend.model.dto.TodoDto;
import com.sgkim.todocalendar.todo_calendar_backend.model.dto.ShareRequestDto;
import com.sgkim.todocalendar.todo_calendar_backend.model.dto.TodoDescriptionDto;
import com.sgkim.todocalendar.todo_calendar_backend.service.OutlookMailService;
import com.sgkim.todocalendar.todo_calendar_backend.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/todo")
public class TodoBackendController {

    private final TodoService todoService;
    private final OutlookMailService mailService;

    public TodoBackendController(TodoService todoService, OutlookMailService mailService) {
        this.todoService = todoService;
        this.mailService = mailService;
    }

    // 1. 전체 조회(/api/todo)
    @Operation(summary = "전체 할 일(TODO) 목록 조회.", description = "등록된 모든 할 일(TODO) 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getTodos(@RequestParam(defaultValue = "asc") String order) {
        List<TodoDto> todos = todoService.getAllTodos(order);
        return ResponseEntity.ok(todos);
    }

    // 2. 특정 ID 조회
    @Operation(summary = "특정 할 일(TODO) 목록 조회.", description = "등록된 할 일(TODO)중 ID값으로 항목을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    // 3. todo 생성(추가)
    @Operation(summary = "할 일(TODO) 추가", description = "새로운 할 일 항목을 추가합니다.")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", schema = @Schema(implementation = TodoDto.class), examples = @ExampleObject(name = "PostExample", value = """
                    {
                        "title": "test",
                        "dueDate": "2025-03-07"
                    }
                    """))) TodoDto todo) {
        return ResponseEntity.ok(todoService.saveTodo(todo));
    }

    // 4. PUT 요청 → 전체 수정
    @Operation(summary = "할 일(TODO) 전체 수정", description = "ID를 기반으로 할 일을 전체 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable Long id,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", schema = @Schema(implementation = TodoDto.class), examples = @ExampleObject(name = "PutExample", value = """
                    {
                        "title": "test",
                        "dueDate": "2025-03-07",
                        "status": "N"
                    }
                    """))) TodoDto todoDto) {
        TodoDto updatedTodo = todoService.putTodo(id, todoDto);
        return ResponseEntity.ok(updatedTodo);
    }

    // 5. PATCH 요청 → 부분 수정
    @Operation(summary = "할 일(TODO) 부분 수정", description = "ID를 기반으로 특정 할 일의 status를 수정합니다.")
    @PatchMapping("/{id}")
    public ResponseEntity<TodoDto> patchTodo(@PathVariable Long id,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", schema = @Schema(implementation = TodoDto.class), examples = @ExampleObject(name = "PatchExample", value = """
                    {
                        "status": "N"
                    }
                    """))) TodoDto updates) {
        TodoDto patchTodo = todoService.patchTodo(id, updates);
        return ResponseEntity.ok(patchTodo);
    }

    // 6. todo 삭제-delete
    @Operation(summary = "할 일(TODO) 삭제", description = "ID를 기반으로 특정 할 일을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
    
    // 모든 설명 목록 조회
    @Operation(summary = "모든 설명 조회", description = "모든 설명을 조회합니다.")
    @GetMapping("/descriptions")
    public ResponseEntity<List<TodoDescriptionDto>> getTodoDescriptions() {
        return ResponseEntity.ok(todoService.getTodoDescriptions());
    }

    // 부모 TODO의 ID(todoId)로 연결된 설명 목록 조회
    @Operation(summary = "특정 TodoId의 모든 설명 조회", description = "특정 TodoId의 모든 설명을 조회합니다.")
    @GetMapping("/descriptions/{todoId}")
    public ResponseEntity<List<TodoDescriptionDto>> getTodoDescriptionsByTodoId(@PathVariable Long todoId) {
        return ResponseEntity.ok(todoService.getTodoDescriptionsByTodoId(todoId));
    }

    // todo-Description(설명) 생성(추가)
    @Operation(summary = "설명 추가", description = "설명을 추가합니다.")
    @PostMapping("/descriptions/{todoId}")
    public ResponseEntity<TodoDescriptionDto> addTodoDescriptions(@PathVariable Long todoId,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", schema = @Schema(implementation = TodoDescriptionDto.class), examples = @ExampleObject(name = "PostExample", value = """
                    {
                        "description": "하위 설명."
                    }
                    """))) TodoDescriptionDto todoDescription) {
        return ResponseEntity.ok(todoService.saveTodoDescriptions(todoId, todoDescription));
    }

    // todo-Description(설명) 수정-PUT
    @Operation(summary = "설명 수정", description = "설명의 내용을 수정합니다.")
    @PutMapping("/descriptions/{descriptionId}") // descriptionId를 기반으로 수정
    public ResponseEntity<TodoDescriptionDto> updateTodoDescription(
            @PathVariable Long descriptionId,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", schema = @Schema(implementation = TodoDescriptionDto.class),
                    examples = @ExampleObject(name = "PutExample", value = """
                            {
                                "description": "설명 수정입니다~"
                            }
                            """))) TodoDescriptionDto descriptionDto) {
        return ResponseEntity.ok(todoService.updateTodoDescription(descriptionId, descriptionDto));
    }

    // todo-Description(설명) 삭제-delete
    @Operation(summary = "설명 삭제", description = "ID를 기반으로 특정 설명을 삭제합니다.")
    @DeleteMapping("/descriptions/{descriptionId}")
    public ResponseEntity<Void> deleteTodoDescriptions(@PathVariable Long descriptionId) {
        todoService.deleteTodoDescriptions(descriptionId);
        return ResponseEntity.noContent().build();
    }

    // (추가기능) 메일보내기
    @Operation(summary = "할 일(TODO) 메일 공유", description = "이메일(Email)을 받아서 해당 메일로 전송.")
    @PostMapping("/share/mail")
    public ResponseEntity<String> shareTodo(@RequestBody ShareRequestDto request) {
        List<Long> todoIds = request.getTodoIds();
        String email = request.getEmail();

        if (todoIds == null || todoIds.isEmpty()) {
            return ResponseEntity.badRequest().body("공유할 할 일을 선택하세요.");
        }

        StringBuilder content = new StringBuilder();
        content.append(
                "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #f9f9f9;'>")
                .append("<h2 style='color: #2c3e50; text-align: center;'>📌 공유된 할 일 목록</h2>");

        for (Long todoId : todoIds) {
            TodoDto todo = todoService.getTodoById(todoId);
            List<TodoDescriptionDto> todoDescriptions = todoService.getTodoDescriptionsByTodoId(todoId);

            content.append(
                    "<div style='background: #ffffff; padding: 15px; border-radius: 8px; margin-bottom: 15px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);'>")
                    .append("<h3 style='color: #e74c3c;'>").append(todo.getTitle()).append("</h3>")
                    .append("<p style='font-size: 14px; color: #555;'><strong>📅 마감일:</strong> ")
                    .append(todo.getDueDate()).append("</p>");

            // 설명 목록 추가
            if (!todoDescriptions.isEmpty()) {
                content.append("<ul style='padding-left: 20px; color: #333;'>");
                for (TodoDescriptionDto desc : todoDescriptions) {
                    content.append("<li style='margin-bottom: 5px;'>").append(desc.getDescription()).append("</li>");
                }
                content.append("</ul>");
            }

            content.append("<p style='text-align: right;'><a href='http://localhost:3000/")
                    .append(todoId)
                    .append("' style='color: #3498db; text-decoration: none; font-weight: bold;'>🔗 할 일 보기</a></p>")
                    .append("</div>");
        }

        content.append("</div>");

        mailService.sendEmail(email, "📌 ToDo 공유 목록", content.toString());
        return ResponseEntity.ok("이메일 전송 완료");
    }

}
