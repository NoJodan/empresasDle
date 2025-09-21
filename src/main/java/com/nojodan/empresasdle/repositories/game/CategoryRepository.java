package com.nojodan.empresasdle.repositories.game;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nojodan.empresasdle.models.game.Category;
import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByThemeId(Long themeId);
    Optional<Category> findByThemeIdAndName(Long themeId, String name);
}
