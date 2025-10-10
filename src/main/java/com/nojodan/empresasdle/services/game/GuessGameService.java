package com.nojodan.empresasdle.services.game;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nojodan.empresasdle.models.ServiceResponse;
import com.nojodan.empresasdle.models.game.Theme;
import com.nojodan.empresasdle.repositories.game.ThemeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuessGameService {

    private final ThemeRepository themeRepository;

    // Obtener todas las temáticas
    public ServiceResponse<List<Theme>> getAllThemes() {
        List<Theme> themes = themeRepository.findAll();

        if (themes.isEmpty())
            return new ServiceResponse<>(true, "No hay temáticas registradas", List.of());

        return new ServiceResponse<>(true, "Temáticas obtenidas con éxito", themes);
    }
}
