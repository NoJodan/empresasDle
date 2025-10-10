package com.nojodan.empresasdle.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateGuessItemRequest {
    @NotNull(message = "El ID del tema no puede estar vacío")
    private Long themeId;

    @NotBlank(message = "El nombre del ítem no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre del ítem debe tener entre 2 y 50 caracteres")
    private String name;
}
