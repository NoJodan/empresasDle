package com.nojodan.empresasdle.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateThemeRequest {
    @NotBlank(message = "El nombre del tema no puede estar vacío")
    private String name;
}
