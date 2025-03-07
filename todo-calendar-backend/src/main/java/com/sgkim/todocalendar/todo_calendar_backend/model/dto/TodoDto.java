package com.sgkim.todocalendar.todo_calendar_backend.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema
@Getter @Setter
@AllArgsConstructor  // ✅ 모든 필드를 포함하는 생성자 자동 생성
@NoArgsConstructor   // ✅ 기본 생성자 추가 (Jackson이 필요로 함)
public class TodoDto {
    private Long id;
    private String title;
    private LocalDate dueDate;
    private String status = "N";
    @Schema(hidden = true)
    private LocalDateTime createDateTime;
    @Schema(hidden = true)
    private LocalDateTime updateDateTime;

    // Entity를 받아서 DTO로 변환하는 생성자 추가!
    // public TodoDto(TodoEntity entity) {
    //     this.id = entity.getId();
    //     this.title = entity.getTitle();
    //     this.description = entity.getDescription();
    //     this.dueDate = entity.getDueDate();
    //     this.status = entity.getStatus();
    //     this.createDateTime = entity.getCreateDateTime();
    //     this.updateDateTime = entity.getUpdateDateTime();
    // }
}
