package com.sgkim.todocalendar.todo_calendar_backend.converter;



import com.sgkim.todocalendar.todo_calendar_backend.model.Todo.Status;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EnumConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        if (status == null) return null;
        return status.name().toLowerCase(); // ENUM을 소문자로 변환 후 DB 저장
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            return Status.valueOf(dbData.toUpperCase()); // DB 값(소문자)을 ENUM(대문자)으로 변환
        } catch (IllegalArgumentException e) {
            return null; // 잘못된 값이 들어오면 NULL 처리
        }
    }
}
