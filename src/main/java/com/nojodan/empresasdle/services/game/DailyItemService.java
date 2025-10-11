package com.nojodan.empresasdle.services.game;

import com.nojodan.empresasdle.models.ServiceResponse;
import com.nojodan.empresasdle.models.game.DailyItem;
import com.nojodan.empresasdle.models.game.GuessItem;
import com.nojodan.empresasdle.models.game.Theme;
import com.nojodan.empresasdle.repositories.game.DailyItemRepository;
import com.nojodan.empresasdle.repositories.game.GuessItemRepository;
import com.nojodan.empresasdle.repositories.game.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DailyItemService {

    private final DailyItemRepository dailyItemRepository;
    private final GuessItemRepository guessItemRepository;
    private final ThemeRepository themeRepository;
    private final Random random = new Random();

    public ServiceResponse<?> generateDailyItems() {
    List<Theme> themes = themeRepository.findAll();
    themes.forEach(this::generateDailyItem);
    return new ServiceResponse<>(true, "Ítems del día generados", java.util.Collections.emptyList());
    }

    public ServiceResponse<?> generateDailyItemByTheme(Long themeId) {
        Optional<Theme> theme = themeRepository.findById(themeId);
        if (theme.isEmpty()) {
            return new ServiceResponse<>(false, "Tema no encontrado", null);
        }
        generateDailyItem(theme.get());
        return new ServiceResponse<>(true, "Ítem del día generado para el tema " + themeId, null);
    }
    
    public void generateDailyItem(Theme theme) {
        if (dailyItemRepository.findByThemeIdAndDate(theme.getId(), LocalDate.now()).isPresent())
            return;

        List<GuessItem> items = guessItemRepository.findAll()
                .stream()
                .filter(i -> i.getTheme().equals(theme))
                .toList();

        if (items.isEmpty()) return;

        GuessItem selected = items.get(random.nextInt(items.size()));

        DailyItem daily = new DailyItem();
        daily.setTheme(theme);
        daily.setGuessItem(selected);
        daily.setDate(LocalDate.now());
        dailyItemRepository.save(daily);
    }

    public ServiceResponse<?> getDailyItem(Long themeId) {
        Optional<DailyItem> dailyItem = dailyItemRepository.findByThemeIdAndDate(themeId, LocalDate.now());
        if (dailyItem.isEmpty()) {
            return new ServiceResponse<>(false, "No hay ítem del día configurado para este tema", null);
        }
        return new ServiceResponse<>(true, "Ítem del día encontrado", dailyItem.get());
    }
}
