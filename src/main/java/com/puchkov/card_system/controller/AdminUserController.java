package com.puchkov.card_system.controller;

import com.puchkov.card_system.dto.UserDto;
import com.puchkov.card_system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("admin/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
@Tag(
        name = "Система управления пользователями",
        description = "Позволяет администратору просматривать пользователей, обновлять их данные, и удалять их")
public class AdminUserController {
    private final UserService userService;
    @Operation(
            summary = "Получение списка пользователей",
            description = "Позволяет получить список пользователй в системе, с фильтрацией и пагинацией"
    )
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }
    @Operation(
            summary = "Обновление данных пользователя",
            description = "Позволяет обновить данные пользователя в системе"
    )
    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto) {
        return userService.update(userDto);
    }

    @Operation(
            summary = "Удаление пользователя",
            description = "Позволяет Удалить пользователя"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
