package com.nojodan.empresasdle.repositories.game;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nojodan.empresasdle.models.game.DailyItem;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyItemRepository extends JpaRepository<DailyItem, Long>{
    Optional<DailyItem> findByThemeIdAndDate(Long themeId, LocalDate date);
}
