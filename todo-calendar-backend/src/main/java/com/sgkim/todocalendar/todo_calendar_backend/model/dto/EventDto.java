package com.sgkim.todocalendar.todo_calendar_backend.model.dto;

import java.time.LocalDateTime;

import com.sgkim.todocalendar.todo_calendar_backend.model.entity.EventEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor  // ✅ 모든 필드를 포함하는 생성자 자동 생성
@NoArgsConstructor   // ✅ 기본 생성자 추가 (Jackson이 필요로 함)
public class EventDto {
    @Schema(hidden = true)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String location;
    @Schema(hidden = true)
    private LocalDateTime createDateTime;
    @Schema(hidden = true)
    private LocalDateTime updateDateTime;

    // Entity를 받아서 DTO로 변환하는 생성자 추가!
    // public EventDto(EventEntity entity) {
    //     this.id = entity.getId();
    //     this.title = entity.getTitle();
    //     this.description = entity.getDescription();
    //     this.startDateTime = entity.getStartDateTime();
    //     this.endDateTime = entity.getEndDateTime();
    //     this.location = entity.getLocation();
    //     this.createDateTime = entity.getCreateDateTime();
    //     this.updateDateTime = entity.getUpdateDateTime();
    // }
}
