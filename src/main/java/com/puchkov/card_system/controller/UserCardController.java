package com.puchkov.card_system.controller;

import com.puchkov.card_system.dto.TransferRequestDto;
import com.puchkov.card_system.service.UserCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("cards")
@SecurityRequirement(name = "JWT")
@Tag(
        name = "Система карт пользователя",
        description = "Позволяет просматривать список карт пользователя и запрашивать блокировку карт, и осуществлять перевод между своими картами")

public class UserCardController {

    private final UserCardService userCardService;

    @Operation(
            summary = "Получение списка карт пользователя",
            description = "Позволяет получить список всех карт пользователя, с фильтрацией и пагинацией"
    )
    @GetMapping
    public ResponseEntity<?> getUserCards(HttpServletRequest request, @Nullable @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(userCardService.getUserCards(request, pageable));
    }

    @Operation(
            summary = "Запрос блокировки",
            description = "Позволяет отправить запрос на блокировку карты (изменяет статус на 'PENDING_BLOCK')"
    )
    @PutMapping("/{card_id}/block")
    public ResponseEntity<?> blockCard(HttpServletRequest request, @PathVariable Long card_id) {
        return userCardService.blockCard(card_id, request);
    }

    @Operation(
            summary = "Перевод между картами пользователя",
            description = "Осуществляет перевод средств с одной карты пользователя на другую"
    )
    @PostMapping
    public String transfer(HttpServletRequest request, @Valid @RequestBody TransferRequestDto transferRequestDto) {
        return userCardService.transfer(request, transferRequestDto);
    }
}
