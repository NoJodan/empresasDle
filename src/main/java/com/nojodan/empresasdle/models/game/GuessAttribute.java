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
@Table(name = "guess_attributes")
public class GuessAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El valor del atributo no puede estar vacío")
    @Size(min = 2, max = 100, message = "El valor del atributo debe tener entre 2 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String value;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "guess_item_id", nullable = false)
    private GuessItem guessItem;
}
