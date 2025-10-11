package com.nojodan.empresasdle.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GuessRequest {
    @NotNull(message = "La temática no puede estar vacía")
    private Long themeId;

    @NotBlank(message = "La adivinanza no puede estar vacía")
    private String itemName;
}
