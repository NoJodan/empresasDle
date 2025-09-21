package com.nojodan.empresasdle.models.game;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "guess_items")
public class GuessItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del ítem no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre del ítem debe tener entre 2 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "theme_id", nullable = false)
    private Theme theme;

    @OneToMany(mappedBy = "guessItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GuessAttribute> attributes;
}
