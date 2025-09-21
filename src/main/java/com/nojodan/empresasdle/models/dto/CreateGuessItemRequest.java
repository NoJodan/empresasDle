package com.nojodan.empresasdle.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateGuessItemRequest {
    @NotBlank(message = "El nombre del item no puede estar vacío")
    private String name;
}
