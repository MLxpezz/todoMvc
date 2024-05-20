package com.todo.app.model.entities.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
    Long id,
    @NotBlank(message = "El campo usuario es requerido.") String username,
    @NotBlank(message = "El campo Nueva contraseña es requerido.") String newPassword,
    @NotBlank(message = "El campo Contraseña actual es requerido.") String currentPassword
) {
    
}
