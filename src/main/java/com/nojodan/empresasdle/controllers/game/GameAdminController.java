package com.nojodan.empresasdle.controllers.game;

import com.nojodan.empresasdle.models.dto.*;
import com.nojodan.empresasdle.services.game.DailyItemService;
import com.nojodan.empresasdle.services.game.GameAdminService;
import com.nojodan.empresasdle.utils.ResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/game")
@RequiredArgsConstructor
public class GameAdminController {

    private final GameAdminService gameAdminService;
    private final DailyItemService dailyItemService;

    @PostMapping("/themes")
    public ResponseEntity<?> createTheme(@Valid @RequestBody CreateThemeRequest request) {
        return ResponseBuilder.buildCreatedResponse(
                gameAdminService.createTheme(request.getName()),
                "theme"
        );
    }

    @GetMapping("/themes")
    public ResponseEntity<?> getAllThemes() {
        return ResponseBuilder.buildResponse(
                gameAdminService.getAllThemes(),
                "themes"
        );
    }

    // Modificar Theme
    @PutMapping("/themes/{themeId}")
    public ResponseEntity<?> updateTheme(@PathVariable Long themeId, @RequestBody CreateThemeRequest request) {
        return ResponseBuilder.buildResponse(
                gameAdminService.updateTheme(themeId, request.getName()),
                "theme"
        );
    }
    
    // Eliminar Theme
    @DeleteMapping("/themes/{themeId}")
    public ResponseEntity<?> deleteTheme(@PathVariable Long themeId) {
        return ResponseBuilder.buildResponse(
                gameAdminService.deleteTheme(themeId),
                "theme"
        );
    }

    @PostMapping("/themes/categories")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        return ResponseBuilder.buildCreatedResponse(
                gameAdminService.createCategory(request.getThemeId(), request.getName()),
                "category"
        );
    }

    // No cambiar a request body porque es un GET
    @GetMapping("/themes/{themeId}/categories")
    public ResponseEntity<?> getCategories(@PathVariable Long themeId) {
        return ResponseBuilder.buildResponse(
                gameAdminService.getCategoriesByTheme(themeId),
                "categories"
        );
    }

        // Modificar Category
        @PutMapping("/categories/{categoryId}")
        public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @RequestBody CreateCategoryRequest request) {
            return ResponseBuilder.buildResponse(
                    gameAdminService.updateCategory(categoryId, request.getName()),
                    "category"
            );
        }

        // Eliminar Category
        @DeleteMapping("/categories/{categoryId}")
        public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
            return ResponseBuilder.buildResponse(
                    gameAdminService.deleteCategory(categoryId),
                    "category"
            );
        }

    @PostMapping("/themes/items")
    public ResponseEntity<?> createGuessItem(@Valid @RequestBody CreateGuessItemRequest request) {
        return ResponseBuilder.buildCreatedResponse(
                gameAdminService.createGuessItem(request.getThemeId(), request.getName()),
                "item"
        );
    }
    
    // No cambiar a request body porque es un GET
    @GetMapping("/themes/{themeId}/items")
    public ResponseEntity<?> getItems(@PathVariable Long themeId) {
        return ResponseBuilder.buildResponse(
                gameAdminService.getItemsByTheme(themeId),
                "items"
        );
    }

        // Modificar GuessItem
        @PutMapping("/items/{itemId}")
        public ResponseEntity<?> updateGuessItem(@PathVariable Long itemId, @RequestBody CreateGuessItemRequest request) {
            return ResponseBuilder.buildResponse(
                    gameAdminService.updateGuessItem(itemId, request.getName()),
                    "item"
            );
        }

        // Eliminar GuessItem
        @DeleteMapping("/items/{itemId}")
        public ResponseEntity<?> deleteGuessItem(@PathVariable Long itemId) {
            return ResponseBuilder.buildResponse(
                    gameAdminService.deleteGuessItem(itemId),
                    "item"
            );
        }

    @PostMapping("/themes/categories/items/attributes")
    public ResponseEntity<?> createAttribute(@Valid @RequestBody CreateAttributeRequest request) {
        return ResponseBuilder.buildCreatedResponse(
                gameAdminService.createAttribute(request.getThemeId(), request.getCategoryId(), request.getItemId(), request.getValue()),
                "attribute"
        );
    }

    // No cambiar a request body porque es un GET
    @GetMapping("/themes/{themeId}/categories/{categoryId}/items/{itemId}/attributes")
    public ResponseEntity<?> getAttributes(@PathVariable Long themeId, @PathVariable Long categoryId, @PathVariable Long itemId) {
        return ResponseBuilder.buildResponse(
                gameAdminService.getAttributesByItemAndThemeId(itemId, categoryId, themeId),
                "attributes"
        );
    }

        // Modificar Attribute
        @PutMapping("/attributes/{attributeId}")
        public ResponseEntity<?> updateAttribute(@PathVariable Long attributeId, @RequestBody CreateAttributeRequest request) {
            return ResponseBuilder.buildResponse(
                    gameAdminService.updateAttribute(attributeId, request.getValue()),
                    "attribute"
            );
        }

        // Eliminar Attribute
        @DeleteMapping("/attributes/{attributeId}")
        public ResponseEntity<?> deleteAttribute(@PathVariable Long attributeId) {
            return ResponseBuilder.buildResponse(
                    gameAdminService.deleteAttribute(attributeId),
                    "attribute"
            );
        }
    
    @PostMapping("/daily/generate")
    public ResponseEntity<?> generateDailyItems() {
        return ResponseBuilder.buildResponse(
                dailyItemService.generateDailyItems(),
                "dailyItems"
        );
    }

    @PostMapping("/{themeId}/daily/generate")
    public ResponseEntity<?> generateDailyItemByTheme(@PathVariable Long themeId) {
        return ResponseBuilder.buildResponse(
            dailyItemService.generateDailyItemByTheme(themeId),
            "dailyItem"
        );
    }

    @GetMapping("/{themeId}/daily")
    public ResponseEntity<?> getDailyItem(@PathVariable Long themeId) {
        return ResponseBuilder.buildResponse(
                dailyItemService.getDailyItem(themeId),
                "dailyItem"
        );
    }
    
}
