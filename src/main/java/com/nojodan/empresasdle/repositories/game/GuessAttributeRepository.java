package com.nojodan.empresasdle.repositories.game;

import com.nojodan.empresasdle.models.game.GuessAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuessAttributeRepository extends JpaRepository<GuessAttribute, Long> {
    Optional<GuessAttribute> findByGuessItemIdAndCategoryId(Long itemId, Long categoryId);
}
