package com.nojodan.empresasdle.services;

import com.nojodan.empresasdle.models.User;
import com.nojodan.empresasdle.models.ServiceResponse;
import com.nojodan.empresasdle.repositories.UserRepository;
import com.nojodan.empresasdle.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ServiceResponse<String> register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return new ServiceResponse<>(false, "El nombre de usuario ya está en uso", null);
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ServiceResponse<>(false, "El email ya está registrado", null);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return new ServiceResponse<>(true, "Usuario registrado con éxito", savedUser.getUsername());
    }

    public ServiceResponse<String> authenticate(String identifier, String rawPassword) {
        Optional<User> user = userRepository.findByUsernameOrEmail(identifier, identifier);

        if (user.isEmpty()) {
            return new ServiceResponse<>(false, "Usuario no encontrado", null);
        }

        if (!passwordEncoder.matches(rawPassword, user.get().getPassword())) {
            return new ServiceResponse<>(false, "Contraseña incorrecta", null);
        }

        String token = jwtUtil.generateToken(user.get().getUsername());
        return new ServiceResponse<>(true, "Login exitoso", token);
    }
}
