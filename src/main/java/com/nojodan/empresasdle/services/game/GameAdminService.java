package com.nojodan.empresasdle.services.game;

import com.nojodan.empresasdle.models.ServiceResponse;
import com.nojodan.empresasdle.models.game.*;
import com.nojodan.empresasdle.repositories.game.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameAdminService {

    private final ThemeRepository themeRepository;
    private final CategoryRepository categoryRepository;
    private final GuessItemRepository guessItemRepository;
    private final GuessAttributeRepository guessAttributeRepository;

    // Crear temática
    public ServiceResponse<Theme> createTheme(String name) {
        if (themeRepository.findByName(name).isPresent()) {
            return new ServiceResponse<>(false, "La temática ya existe", null);
        }

        Theme theme = Theme.builder().name(name).build();
        Theme saved = themeRepository.save(theme);
        return new ServiceResponse<>(true, "Temática creada con éxito", saved);
    }

    // Crear categoría
    public ServiceResponse<Category> createCategory(Long themeId, String name) {
        Optional<Theme> theme = themeRepository.findById(themeId);
        if (theme.isEmpty()) {
            return new ServiceResponse<>(false, "Tema no encontrado", null);
        }

        if (categoryRepository.findByThemeIdAndName(themeId, name).isPresent()) {
            return new ServiceResponse<>(false, "La categoría ya existe en esta temática", null);
        }

        Category category = Category.builder()
                .name(name)
                .theme(theme.get())
                .build();
        Category saved = categoryRepository.save(category);
        return new ServiceResponse<>(true, "Categoría creada con éxito", saved);
    }

    // Crear item
    public ServiceResponse<GuessItem> createGuessItem(Long themeId, String name) {
        Optional<Theme> theme = themeRepository.findById(themeId);
        if (theme.isEmpty()) {
            return new ServiceResponse<>(false, "Tema no encontrado", null);
        }

        if (guessItemRepository.findByThemeIdAndName(themeId, name).isPresent()) {
            return new ServiceResponse<>(false, "El item ya existe en esta temática", null);
        }

        GuessItem item = GuessItem.builder()
                .name(name)
                .theme(theme.get())
                .build();
        GuessItem saved = guessItemRepository.save(item);
        return new ServiceResponse<>(true, "Item creado con éxito", saved);
    }

    // Crear atributo
    public ServiceResponse<GuessAttribute> createAttribute(Long itemId, Long categoryId, String value) {
        Optional<GuessItem> item = guessItemRepository.findById(itemId);
        if (item.isEmpty()) {
            return new ServiceResponse<>(false, "Item no encontrado", null);
        }

        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            return new ServiceResponse<>(false, "Categoría no encontrada", null);
        }

        if (guessAttributeRepository.findByGuessItemIdAndCategoryId(itemId, categoryId).isPresent()) {
            return new ServiceResponse<>(false, "El atributo ya existe para este item en esta categoría", null);
        }

        GuessAttribute attribute = GuessAttribute.builder()
                .guessItem(item.get())
                .category(category.get())
                .value(value)
                .build();
        GuessAttribute saved = guessAttributeRepository.save(attribute);
        return new ServiceResponse<>(true, "Atributo creado con éxito", saved);
    }

    // Obtener todas las temáticas
    public ServiceResponse<List<Theme>> getAllThemes() {
        List<Theme> themes = themeRepository.findAll();

        if(themes.isEmpty()) return new ServiceResponse<>(true, "No hay temáticas registradas", List.of());

        return new ServiceResponse<>(true, "Temáticas obtenidas con éxito", themes);
    }

    // Obtener categorías de un tema
    public ServiceResponse<List<Category>> getCategoriesByTheme(Long themeId) {
        List<Category> categories = categoryRepository.findByThemeId(themeId);

        if(categories.isEmpty()) return new ServiceResponse<>(true, "No hay categorías registradas para este tema", List.of());

        return new ServiceResponse<>(true, "Categorías obtenidas", categories);
    }

    // Obtener items de un tema
    public ServiceResponse<List<GuessItem>> getItemsByTheme(Long themeId) {
        List<GuessItem> items = guessItemRepository.findByThemeId(themeId);

        if(items.isEmpty()) return new ServiceResponse<>(true, "No hay items registrados para este tema", List.of());

        return new ServiceResponse<>(true, "Items obtenidos", items);
    }

    public ServiceResponse<List<GuessAttribute>> getAttributesByItem(Long itemId, Long categoryId) {
        Optional<GuessAttribute> attributes = guessAttributeRepository.findByGuessItemIdAndCategoryId(itemId, categoryId);

        if(attributes.isEmpty()) return new ServiceResponse<>(true, "No hay atributos registrados", List.of());

        return new ServiceResponse<>(true, "Atributos obtenidos", List.of(attributes.get()));
    }
}
