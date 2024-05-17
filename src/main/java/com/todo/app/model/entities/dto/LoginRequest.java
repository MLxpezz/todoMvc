package com.todo.app.model.entities.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest
                        (
                            @NotBlank(message = "El campo usuario es requerido.") String username,
                            @NotBlank(message = "El campo contraseña es requerido.") String password
                        ) 
{
    
}
