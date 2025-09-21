package com.nojodan.empresasdle.utils;

import com.nojodan.empresasdle.models.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ResponseBuilder {

    private ResponseBuilder() {
        // Utility class, no instancias
    }

    public static <T> ResponseEntity<?> buildResponse(ServiceResponse<T> response, String key) {
        if (!response.isSuccess()) {
            // Si falla, asumimos que es conflicto o not found según el mensaje
            HttpStatus status = response.getMessage().toLowerCase().contains("no encontrado")
                    ? HttpStatus.NOT_FOUND
                    : HttpStatus.CONFLICT;

            return ResponseEntity.status(status)
                    .body(Map.of("error", response.getMessage()));
        }

        return ResponseEntity.ok(Map.of(
                "message", response.getMessage(),
                key, response.getData()
        ));
    }

    public static <T> ResponseEntity<?> buildCreatedResponse(ServiceResponse<T> response, String key) {
        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", response.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", response.getMessage(),
                        key, response.getData()
                ));
    }
}
