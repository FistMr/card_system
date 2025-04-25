package com.puchkov.card_system.controller;

import com.puchkov.card_system.dto.*;
import com.puchkov.card_system.service.AdminCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("admin/cards")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
@Tag(
        name = "Система карт администратора",
        description = "Позволяет создавать, просматривать, удалять, задавать лимиты, менять статус и удалять карты, просматривать транзакции карт (Необходимы права администратора)")
public class AdminCardController {

    private final AdminCardService adminCardService;

    @Operation(
            summary = "Получение списка карт",
            description = "Позволяет получить список всех карт в системе, с фильтрацией и пагинацией"
    )
    @GetMapping
    public ResponseEntity<?> getAllCards(
            @Nullable @Valid @ModelAttribute CardFilter filter,
            @Nullable @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(adminCardService.findAll(filter, pageable));
    }

    @Operation(
            summary = "Создание новой карты",
            description = "Позволяет администратору создать для пользователя новую карту по его данным"
    )
    @PostMapping
    public ResponseEntity<?> createNewCard(@Valid @RequestBody NewCardDto newCardDto) {
        return adminCardService.createNewCard(newCardDto);
    }

    @Operation(
            summary = "Изменение статуса",
            description = "Позволяет администратору изменить статус карты"
    )
    @PatchMapping("/status")
    public ResponseEntity<?> setStatus(@Valid @RequestBody NewCardStatusDto newCardStatusDto) {
        return adminCardService.setStatus(newCardStatusDto);
    }

    @Operation(
            summary = "Изменение лимитов",
            description = "Позволяет администратору изменять лимиты для карты"
    )
    @PatchMapping("/limits")
    public ResponseEntity<?> updateLimits(
            @Valid @RequestBody CardLimitsDto cardLimitsDto) {
        return adminCardService.updateLimits(cardLimitsDto);
    }

    @Operation(
            summary = "Удаление карты",
            description = "Позволяет администратору удалить карту"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id) {
        adminCardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Просмотр транзакций",
            description = "Позволяет администратору удалить карту"
    )
    @GetMapping("/transactions")
    public ResponseEntity<?> getAllTransaction(
            @Nullable @Valid @ModelAttribute TransactionFilter filter,
            @Nullable @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(adminCardService.findAllTransaction(filter, pageable));
    }
}
