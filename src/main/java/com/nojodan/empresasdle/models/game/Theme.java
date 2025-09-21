package com.nojodan.empresasdle.models.game;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "themes")
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del tema no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre del tema debe tener entre 3 y 50 caracteres")
    @Column(nullable = false, unique = true, length = 50)
    private String name;
}
