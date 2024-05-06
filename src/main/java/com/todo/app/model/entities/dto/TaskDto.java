package com.todo.app.model.entities.dto;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record TaskDto(  Long id,
                        String title, 
                        String description,
                        boolean isCompleted,
                        LocalDate createdAt, 
                        Long userId) {
    
}
