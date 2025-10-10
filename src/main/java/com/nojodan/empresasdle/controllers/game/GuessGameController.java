package com.nojodan.empresasdle.controllers.game;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nojodan.empresasdle.services.game.GuessGameService;
import com.nojodan.empresasdle.utils.ResponseBuilder;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

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

}
