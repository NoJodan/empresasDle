package com.nojodan.empresasdle.controllers.game;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nojodan.empresasdle.models.dto.GuessRequest;
import com.nojodan.empresasdle.services.game.GuessGameService;
import com.nojodan.empresasdle.utils.ResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GuessGameController {
    private final GuessGameService guessGameService;

    @GetMapping("/themes")
    public ResponseEntity<?> getAllThemes() {
        return ResponseBuilder.buildResponse(
                guessGameService.getAllThemes(),
                "themes");
    }

    @PostMapping("/guess")
    public ResponseEntity<?> makeGuess(@Valid @RequestBody GuessRequest request) {
        return ResponseBuilder.buildResponse(
                guessGameService.makeGuess(request.getThemeId(), request.getItemName()),
                "guessResult");
    }

}
