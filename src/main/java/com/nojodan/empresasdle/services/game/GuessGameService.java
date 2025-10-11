package com.nojodan.empresasdle.services.game;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.nojodan.empresasdle.models.ServiceResponse;
import com.nojodan.empresasdle.models.dto.AttributeComparison;
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
        if (dailyItem.isEmpty())
            return new ServiceResponse<>(false, "No hay un item configurado para esta tematica", null);

        GuessItem targetItem = dailyItem.get().getGuessItem();

        Optional<GuessItem> guessedItem = guessItemRepository.findByNameIgnoreCaseAndThemeId(itemName, themeId);
        if (guessedItem.isEmpty())
            return new ServiceResponse<>(false, "Item no encontrado", null);

        List<GuessAttribute> guessedAttributes = guessedItem.get().getAttributes();
        List<GuessAttribute> correctAttributes = targetItem.getAttributes();
        List<AttributeComparison> comparisons = new ArrayList<>();

        for (GuessAttribute guessedAttr : guessedAttributes) {
            String attributeName = guessedAttr.getCategory().getName();
            String guessedValue = guessedAttr.getValue();

            GuessAttribute correctAttr = correctAttributes.stream()
                    .filter(a -> a.getCategory().getId().equals(guessedAttr.getCategory().getId()))
                    .findFirst()
                    .orElse(null);

            if (correctAttr == null)
                continue;

            String hint = generatrHint(guessedValue, correctAttr.getValue());

            comparisons.add(new AttributeComparison(
                    attributeName,
                    guessedValue,
                    hint));
        }

        boolean isCompletelyCorrect = comparisons.stream()
                .allMatch(attr -> attr.getHint().equals("Correcto"));
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("correct", isCompletelyCorrect);
        responseData.put("attributeHints", comparisons);
        return new ServiceResponse<>(true, "Adivinanza procesada", responseData);

    }

    private String generatrHint(String guessedValue, String correctValue) {
        try {
            double guessedNumber = Double.parseDouble(guessedValue);
            double correctNumber = Double.parseDouble(correctValue);

            if (guessedNumber == correctNumber)
                return "Correcto";
            return guessedNumber > correctNumber ? "El valor debe ser menor" : "El valor debe ser mayor";
        } catch (NumberFormatException e) {
            if (guessedValue.equalsIgnoreCase(correctValue)) {
                return "Correcto";
            } else {
                return "Incorrecto";
            }
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
