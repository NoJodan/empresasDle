package com.nojodan.empresasdle.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateThemeRequest {
    @NotBlank(message = "El nombre del tema no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre del tema debe tener entre 3 y 50 caracteres")
    private String name;
}
