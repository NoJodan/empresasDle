package com.nojodan.empresasdle.controllers.game;

import com.nojodan.empresasdle.models.dto.*;
import com.nojodan.empresasdle.services.game.GameAdminService;
import com.nojodan.empresasdle.utils.ResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/admin/game")
@RequiredArgsConstructor
public class GameAdminController {

    private final GameAdminService gameAdminService;

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

    @PostMapping("/themes/categories")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        return ResponseBuilder.buildCreatedResponse(
                gameAdminService.createCategory(request.getThemeId(), request.getName()),
                "category"
        );
    }

    @GetMapping("/themes/{themeId}/categories")
    public ResponseEntity<?> getCategories(@PathVariable Long themeId) {
        return ResponseBuilder.buildResponse(
                gameAdminService.getCategoriesByTheme(themeId),
                "categories"
        );
    }

    @PostMapping("/themes/{themeId}/items")
    public ResponseEntity<?> createGuessItem(@PathVariable Long themeId, @Valid @RequestBody CreateGuessItemRequest request) {
        return ResponseBuilder.buildCreatedResponse(
                gameAdminService.createGuessItem(themeId, request.getName()),
                "item"
        );
    }

    @GetMapping("/themes/{themeId}/items")
    public ResponseEntity<?> getItems(@PathVariable Long themeId) {
        return ResponseBuilder.buildResponse(
                gameAdminService.getItemsByTheme(themeId),
                "items"
        );
    }

    @PostMapping("/items/{itemId}/categories/{categoryId}/attributes")
    public ResponseEntity<?> createAttribute(@PathVariable Long itemId, @PathVariable Long categoryId, @Valid @RequestBody CreateAttributeRequest request) {
        return ResponseBuilder.buildCreatedResponse(
                gameAdminService.createAttribute(itemId, categoryId, request.getValue()),
                "attribute"
        );
    }

    @GetMapping("/items/{itemId}/categories/{categoryId}/attributes")
    public ResponseEntity<?> getAttributes(@PathVariable Long itemId, @PathVariable Long categoryId) {
        return ResponseBuilder.buildResponse(
                gameAdminService.getAttributesByItem(itemId, categoryId),
                "attributes"
        );
    }
    
}
