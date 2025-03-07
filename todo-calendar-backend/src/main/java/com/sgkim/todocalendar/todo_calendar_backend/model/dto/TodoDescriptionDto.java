package com.sgkim.todocalendar.todo_calendar_backend.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "할 일 설명 요청 DTO")
@Getter @Setter
public class TodoDescriptionDto {
    private Long id;
    @Schema(description = "할 일 ID", example = "1")
    private Long todoId;
    @Schema(description = "설명 내용", example = "하위 설명.")
    private String description;
    

}
