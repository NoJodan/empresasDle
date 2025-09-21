package com.nojodan.empresasdle.controllers;

import com.nojodan.empresasdle.models.dto.LoginRequest;
import com.nojodan.empresasdle.models.dto.RegisterRequest;
import com.nojodan.empresasdle.services.AuthService;
import com.nojodan.empresasdle.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class TestAuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseBuilder.buildCreatedResponse(
                authService.register(request.getUsername(), request.getEmail(), request.getPassword()),
                "username"
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return ResponseBuilder.buildResponse(
                authService.authenticate(request.getIdentifier(), request.getPassword()),
                "token"
        );
    }
}
