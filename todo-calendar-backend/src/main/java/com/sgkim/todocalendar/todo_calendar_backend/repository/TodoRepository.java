package com.sgkim.todocalendar.todo_calendar_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgkim.todocalendar.todo_calendar_backend.model.entity.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

        // ✅ 마감일 기준으로 오름차순 정렬된 목록 조회
        List<TodoEntity> findAllByOrderByDueDateAsc();

        // ✅ 마감일 기준으로 내림차순 정렬된 목록 조회
        List<TodoEntity> findAllByOrderByDueDateDesc();

}
