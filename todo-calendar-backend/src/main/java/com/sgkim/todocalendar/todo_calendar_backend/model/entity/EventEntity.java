package com.sgkim.todocalendar.todo_calendar_backend.model.entity;

import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="event")
@Getter @Setter
public class EventEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255, columnDefinition="NOT NULL")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "start_date_time", columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time", columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime endDateTime;

    @Column(name = "location", length = 255)
    private String location;

}
