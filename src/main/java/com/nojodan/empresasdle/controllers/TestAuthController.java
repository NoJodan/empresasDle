package com.nojodan.empresasdle.controllers;

import com.nojodan.empresasdle.models.User;
import com.nojodan.empresasdle.models.dto.LoginRequest;
import com.nojodan.empresasdle.models.ServiceResponse;
import com.nojodan.empresasdle.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class TestAuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        ServiceResponse<String> result = authService.register(user);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", result.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", result.getMessage(), "user", result.getData()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        String identifier = request.getIdentifier();
        String password = request.getPassword();
    
        ServiceResponse<String> result = authService.authenticate(identifier, password);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", result.getMessage()));
        }

        return ResponseEntity.ok(Map.of("message", result.getMessage(), "token", result.getData()));
    }
}
