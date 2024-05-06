package com.todo.app.model.entities.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record UserDto   (Long id,
                        String username,
                        List<TaskDto> list) {
    
}
