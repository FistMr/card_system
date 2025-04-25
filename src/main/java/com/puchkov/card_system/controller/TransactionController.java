package com.puchkov.card_system.controller;

import com.puchkov.card_system.dto.NewTransactionDto;
import com.puchkov.card_system.dto.TransactionFilter;
import com.puchkov.card_system.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("transaction")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
@Tag(
        name = "Система транзакций",
        description = "Обработка новых транзакций и предоставление списка транзакций по карте пользователя")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(
            summary = "Регистрация новых транзакций",
            description = "Регистрирует новую транзакцию в системе, только для карт пользователя"
    )
    @PostMapping
    public ResponseEntity<?> newTransaction(HttpServletRequest request, @Valid @RequestBody NewTransactionDto newTransactionDto) {
        return transactionService.newTransaction(newTransactionDto, request);
    }

    @Operation(
            summary = "Просмотр транзакций пользователя",
            description = "Позволяет пользователю просмотреть совершенные транзакции  по своей карте"
    )
    @GetMapping("/{cardId}")
    public ResponseEntity<?> getUserTransactions(
            HttpServletRequest request, @Nullable @PageableDefault(size = 10, sort = "id") Pageable pageable, TransactionFilter transactionFilter, @PathVariable Long cardId) {
        return ResponseEntity.ok(transactionService.getUserTransactions(request, pageable, transactionFilter, cardId));
    }
}
