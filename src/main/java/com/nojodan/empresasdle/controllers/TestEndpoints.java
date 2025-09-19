package com.nojodan.empresasdle.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestEndpoints {
    // Endpoint público
    @RequestMapping("/public")
    public String publicEndpoint() {
        return "Este es un endpoint público";
    }

    // Endpoint protegido
    @RequestMapping("/protected")
    public String protectedEndpoint() {
        return "Este es un endpoint protegido";
    }
}
