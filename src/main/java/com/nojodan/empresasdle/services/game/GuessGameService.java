package com.nojodan.empresasdle.services.game;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.nojodan.empresasdle.models.ServiceResponse;
import com.nojodan.empresasdle.models.dto.AttributeComparison;
import com.nojodan.empresasdle.models.dto.GuessResponse;
import com.nojodan.empresasdle.models.game.DailyItem;
import com.nojodan.empresasdle.models.game.GuessAttribute;
import com.nojodan.empresasdle.models.game.GuessItem;
import com.nojodan.empresasdle.models.game.Theme;
import com.nojodan.empresasdle.repositories.game.DailyItemRepository;
import com.nojodan.empresasdle.repositories.game.GuessItemRepository;
import com.nojodan.empresasdle.repositories.game.ThemeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuessGameService {

    private final ThemeRepository themeRepository;
    private final GuessItemRepository guessItemRepository;
    private final DailyItemRepository dailyItemRepository;

    public ServiceResponse<?> makeGuess(Long themeId, String itemName) {
        Optional<DailyItem> dailyItem = dailyItemRepository.findByThemeIdAndDate(themeId, LocalDate.now());
        if(dailyItem.isEmpty()) return new ServiceResponse<>(false, "No hay un item configurado para esta tematica", null);

        GuessItem targetItem = dailyItem.get().getGuessItem();

        Optional<GuessItem> guessedItem = guessItemRepository.findByNameIgnoreCaseAndThemeId(itemName, themeId);
        if(guessedItem.isEmpty()) return new ServiceResponse<>(false, "Item no encontrado", null);

        List<AttributeComparison> comparisons = new ArrayList<>();

        for (GuessAttribute guessedAttr : guessedItem.get().getAttributes()) {
            GuessAttribute targetAttr = targetItem.getAttributes().stream()
                    .filter(a -> a.getCategory().equals(guessedAttr.getCategory()))
                    .findFirst()
                    .orElse(null);

            if (targetAttr != null) {
                String result = compareValues(guessedAttr.getValue(), targetAttr.getValue());
                comparisons.add(new AttributeComparison(
                        guessedAttr.getCategory().getName(),
                        guessedAttr.getValue(),
                        targetAttr.getValue(),
                        result
                ));
            }
        }

        boolean correct = guessedItem.get().getId().equals(targetItem.getId());
        GuessResponse data = new GuessResponse(correct, guessedItem.get().getName(), comparisons);
        return new ServiceResponse<>(true, "Adivinanza procesada", data);

    }

    private String compareValues(String userValue, String correctValue) {
        if (userValue.equalsIgnoreCase(correctValue)) return "EQUAL";

        try {
            double userNum = Double.parseDouble(userValue);
            double correctNum = Double.parseDouble(correctValue);
            return (userNum > correctNum) ? "GREATER" : "LESS";
        } catch (NumberFormatException e) {
            return "DIFFERENT";
        }
    }

    // Obtener todas las temáticas
    public ServiceResponse<List<Theme>> getAllThemes() {
        List<Theme> themes = themeRepository.findAll();

        if (themes.isEmpty())
            return new ServiceResponse<>(true, "No hay temáticas registradas", List.of());

        return new ServiceResponse<>(true, "Temáticas obtenidas con éxito", themes);
    }
}
