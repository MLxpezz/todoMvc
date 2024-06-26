package com.todo.app.model.entities.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskDto {

    private Long id;
    
    @NotBlank(message = "El campo titulo es requerido.")
    private String title;

    @NotBlank(message = "El campo descripcion es requerido.")
    private String description;

    private LocalDate createdAt;

    private boolean isCompleted;

    private Long userId;
}
