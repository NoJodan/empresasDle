package com.nojodan.empresasdle.repositories.game;

import com.nojodan.empresasdle.models.game.GuessItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface GuessItemRepository extends JpaRepository<GuessItem, Long> {
    List<GuessItem> findByThemeId(Long themeId);
    Optional<GuessItem> findByThemeIdAndName(Long themeId, String name);
}
