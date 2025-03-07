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
import org.springframework.web.bind.annotation.RestController;

import com.sgkim.todocalendar.todo_calendar_backend.model.dto.EventDto;
import com.sgkim.todocalendar.todo_calendar_backend.service.EventService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/event")
public class EventBackendController {

    private final EventService eventService;

    public EventBackendController(EventService eventService){
        this.eventService = eventService;
    }

    @Operation(summary = "전체 일정(Event) 목록 조회.", description = "등록된 모든 일정(Event) 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<EventDto>> getEvents(){
        List<EventDto> events = eventService.getEvents();
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "특정 일정(Event) 항목 조회.", description = "등록된 특정 일정(Event) 항목을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id){
        EventDto events = eventService.getEventById(id);
        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<EventDto> addEvent(@RequestBody EventDto event){        
        return ResponseEntity.ok(eventService.addEvent(event));
    }

    // 4. PUT 요청 → 전체 수정
    @Operation(summary = "일정(Event) 전체 수정", description = "ID를 기반으로 일정(Event)을 전체 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @RequestBody EventDto todoDto) {
        EventDto updatedTodo = eventService.putEvent(id, todoDto);
        return ResponseEntity.ok(updatedTodo);
    }

    // 5. PATCH 요청 → 부분 수정 (상태만 변경) 현재 PATCH를 뭐를 받아서 어떤거만 수정할지 고민...
    // @Operation(summary = "일정(Event) 부분 수정", description = "ID를 기반으로 특정 일정(Event)의 ***수정합니다.", parameters = {@Parameter(name="status", description = "할 일 상태(예: completed, pending)", example = "pending")})
    // @PatchMapping("/{id}")
    // public ResponseEntity<EventDto> patchEvent(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
    //     EventDto patchTodo = eventService.patchEvent(id, updates);
    //     return ResponseEntity.ok(patchTodo);
    // }

    // 6. todo 삭제-delete
    @Operation(summary = "일정(Event) 삭제", description = "ID를 기반으로 특정 일정(Event)을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
