package com.puchkov.card_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
public class CardLimitsDto {
    @NotNull
    @Schema(description = "id пользователя", example = "1")
    private Long id;
    @NotNull
    @PositiveOrZero
    @Schema(description = "Лимит списание на сутки", example = "150000")
    private BigDecimal dailyLimit;
    @NotNull
    @PositiveOrZero
    @Schema(description = "Лимит списание на месяц", example = "150000000")
    private BigDecimal monthlyLimit;
}
