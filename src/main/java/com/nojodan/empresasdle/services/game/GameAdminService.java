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
    public ServiceResponse<GuessAttribute> createAttribute(Long themeId, Long categoryId, Long itemId, String value) {
        Optional<Theme> theme = themeRepository.findById(themeId);
        if (theme.isEmpty()) {
            return new ServiceResponse<>(false, "Tema no encontrado", null);
        }

        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            return new ServiceResponse<>(false, "Categoría no encontrada", null);
        }
        
        Optional<GuessItem> item = guessItemRepository.findById(itemId);
        if (item.isEmpty()) {
            return new ServiceResponse<>(false, "Item no encontrado", null);
        }

        if (guessAttributeRepository.findByGuessItemIdAndCategoryIdAndThemeId(itemId, categoryId, themeId).isPresent()) {
            return new ServiceResponse<>(false, "El atributo ya existe para este item en esta categoría", null);
        }

        GuessAttribute attribute = GuessAttribute.builder()
                .guessItem(item.get())
                .category(category.get())
                .theme(theme.get())
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

    public ServiceResponse<List<GuessAttribute>> getAttributesByItemAndThemeId(Long itemId, Long categoryId, Long themeId) {
        Optional<GuessAttribute> attributes = guessAttributeRepository.findByGuessItemIdAndCategoryIdAndThemeId(itemId, categoryId, themeId);

        if(attributes.isEmpty()) return new ServiceResponse<>(true, "No hay atributos registrados", List.of());

        return new ServiceResponse<>(true, "Atributos obtenidos", List.of(attributes.get()));
    }

    // Modificar temática
    public ServiceResponse<Theme> updateTheme(Long themeId, String name) {
        Optional<Theme> themeOpt = themeRepository.findById(themeId);
        if (themeOpt.isEmpty()) {
            return new ServiceResponse<>(false, "Tema no encontrado", null);
        }
        Theme theme = themeOpt.get();
        theme.setName(name);
        Theme updated = themeRepository.save(theme);
        return new ServiceResponse<>(true, "Temática modificada con éxito", updated);
    }

    // Eliminar temática
    public ServiceResponse<Boolean> deleteTheme(Long themeId) {
        if (!themeRepository.existsById(themeId)) {
            return new ServiceResponse<>(false, "Tema no encontrado", false);
        }
        themeRepository.deleteById(themeId);
        return new ServiceResponse<>(true, "Temática eliminada con éxito", true);
    }

    // Modificar categoría
    public ServiceResponse<Category> updateCategory(Long categoryId, String name) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isEmpty()) {
            return new ServiceResponse<>(false, "Categoría no encontrada", null);
        }
        Category category = categoryOpt.get();
        category.setName(name);
        Category updated = categoryRepository.save(category);
        return new ServiceResponse<>(true, "Categoría modificada con éxito", updated);
    }

    // Eliminar categoría
    public ServiceResponse<Boolean> deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            return new ServiceResponse<>(false, "Categoría no encontrada", false);
        }
        categoryRepository.deleteById(categoryId);
        return new ServiceResponse<>(true, "Categoría eliminada con éxito", true);
    }

    // Modificar item
    public ServiceResponse<GuessItem> updateGuessItem(Long itemId, String name) {
        Optional<GuessItem> itemOpt = guessItemRepository.findById(itemId);
        if (itemOpt.isEmpty()) {
            return new ServiceResponse<>(false, "Item no encontrado", null);
        }
        GuessItem item = itemOpt.get();
        item.setName(name);
        GuessItem updated = guessItemRepository.save(item);
        return new ServiceResponse<>(true, "Item modificado con éxito", updated);
    }

    // Eliminar item
    public ServiceResponse<Boolean> deleteGuessItem(Long itemId) {
        if (!guessItemRepository.existsById(itemId)) {
            return new ServiceResponse<>(false, "Item no encontrado", false);
        }
        guessItemRepository.deleteById(itemId);
        return new ServiceResponse<>(true, "Item eliminado con éxito", true);
    }

    // Modificar atributo
    public ServiceResponse<GuessAttribute> updateAttribute(Long attributeId, String value) {
        Optional<GuessAttribute> attrOpt = guessAttributeRepository.findById(attributeId);
        if (attrOpt.isEmpty()) {
            return new ServiceResponse<>(false, "Atributo no encontrado", null);
        }
        GuessAttribute attribute = attrOpt.get();
        attribute.setValue(value);
        GuessAttribute updated = guessAttributeRepository.save(attribute);
        return new ServiceResponse<>(true, "Atributo modificado con éxito", updated);
    }

    // Eliminar atributo
    public ServiceResponse<Boolean> deleteAttribute(Long attributeId) {
        if (!guessAttributeRepository.existsById(attributeId)) {
            return new ServiceResponse<>(false, "Atributo no encontrado", false);
        }
        guessAttributeRepository.deleteById(attributeId);
        return new ServiceResponse<>(true, "Atributo eliminado con éxito", true);
    }
}
