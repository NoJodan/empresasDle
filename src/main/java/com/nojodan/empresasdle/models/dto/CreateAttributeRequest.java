package com.nojodan.empresasdle.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAttributeRequest {
    @NotNull(message = "El ID del tema no puede ser nulo")
    @Positive(message = "El ID del tema debe ser un número positivo")
    private Long itemId;

    @NotNull(message = "El ID de la categoría no puede ser nulo")
    @Positive(message = "El ID de la categoría debe ser un número positivo")
    private Long categoryId;

    @NotNull(message = "El ID del tema no puede ser nulo")
    @Positive(message = "El ID del tema debe ser un número positivo")
    private Long themeId;
    
    @NotBlank(message = "El valor del atributo no puede estar vacío")
    @Size(min = 2, max = 100, message = "El valor del atributo debe tener entre 2 y 100 caracteres")
    private String value;
}