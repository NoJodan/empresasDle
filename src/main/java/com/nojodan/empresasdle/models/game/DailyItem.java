package com.nojodan.empresasdle.models.game;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "daily_items")
@Data
public class DailyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id")
    private GuessItem guessItem;

    @Column(nullable = false)
    private LocalDate date;
}
