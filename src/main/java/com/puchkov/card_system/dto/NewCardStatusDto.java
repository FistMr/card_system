package com.puchkov.card_system.dto;

import com.puchkov.card_system.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class NewCardStatusDto {
    @NotNull
    @Schema(description = "id пользователя", example = "1")
    private Long id;
    @NotNull
    @Schema(description = "Новый статус карты", example = "BLOCKED")
    private CardStatus action;
}
