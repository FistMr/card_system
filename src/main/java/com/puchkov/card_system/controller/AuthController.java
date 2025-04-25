package com.puchkov.card_system.controller;

import com.puchkov.card_system.dto.JwtRequest;
import com.puchkov.card_system.dto.RegistrationUserDto;
import com.puchkov.card_system.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(
        name = "Система авторизации и аутентификации",
        description = "Регистрация в системе новых пользователей и их аутентификация")
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "Аутентификация",
            description = "Аутентифицирует пользователя в системе и выдает ему Jwt токен"
    )
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@Valid @RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Регистрация нового пользователя в системе"
    )
    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }
}
