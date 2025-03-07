package com.sgkim.todocalendar.todo_calendar_backend.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ShareRequestDto {

    private String email;
    private List<Long> todoIds;

}
