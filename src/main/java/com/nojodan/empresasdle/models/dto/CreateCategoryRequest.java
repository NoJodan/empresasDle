package com.nojodan.empresasdle.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateCategoryRequest {
    @NotNull(message = "El ID del tema no puede ser nulo")
    @Positive(message = "El ID del tema debe ser un número positivo")
    private Long themeId;

    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    private String name;
}
