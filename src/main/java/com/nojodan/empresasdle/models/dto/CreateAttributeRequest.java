package com.nojodan.empresasdle.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateAttributeRequest {
    @NotNull(message = "El ID del tema no puede ser nulo")
    @Positive(message = "El ID del tema debe ser un número positivo")
    private Long itemId;

    @NotNull(message = "El ID de la categoría no puede ser nulo")
    @Positive(message = "El ID de la categoría debe ser un número positivo")
    private Long categoryId;
    
    @NotBlank(message = "El valor del atributo no puede estar vacío")
    private String value;
}