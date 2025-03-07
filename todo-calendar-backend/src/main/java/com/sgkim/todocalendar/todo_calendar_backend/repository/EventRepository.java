package com.sgkim.todocalendar.todo_calendar_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgkim.todocalendar.todo_calendar_backend.model.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity,Long>{

}
