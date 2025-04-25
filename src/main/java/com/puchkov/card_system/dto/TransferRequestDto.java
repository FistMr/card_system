package com.puchkov.card_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class TransferRequestDto {
    @NotNull
    @Schema(description = "id карты с которой нужно перевести средства", example = "1")
    private Long fromCardId;

    @NotNull
    @Schema(description = "id карты на которую нужно перевести средства", example = "2")
    private Long toCardId;

    @NotNull
    @Positive
    @Schema(description = "Сумма перевода", example = "100")
    private BigDecimal amount;

    @Schema(description = "Описание транзакции", example = "перевод средств")
    @Size(max = 250, message = "Не больше 250 символов")
    private String description;
}
