package com.nojodan.empresasdle.repositories.game;

import com.nojodan.empresasdle.models.game.Theme;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Optional<Theme> findByName(String name);
}