package com.sgkim.todocalendar.todo_calendar_backend.model.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass // 공통 엔티티로 설정 (상속 시 필드 공유 가능)
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 활성화
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false) // 생성일이 업데이트 될 때 변경되지 않도록 설정
    private LocalDateTime create_date_Time;
    
    @LastModifiedDate
    private LocalDateTime update_date_time;

}
