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

    // No cambiar a request body porque es un GET
    @GetMapping("/themes/{themeId}/categories")
    public ResponseEntity<?> getCategories(@PathVariable Long themeId) {
        return ResponseBuilder.buildResponse(
                gameAdminService.getCategoriesByTheme(themeId),
                "categories"
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
    
}
