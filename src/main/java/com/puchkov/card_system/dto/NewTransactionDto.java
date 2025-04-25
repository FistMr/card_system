package com.puchkov.card_system.dto;

import com.puchkov.card_system.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class NewTransactionDto {
    @NotNull
    @Schema(description = "id карты пользователя", example = "1")
    private Long cardId;

    @Schema(description = "Сумма транзакции", example = "100")
    private BigDecimal amount;

    @Size(max = 250, message = "Не больше 250 символов")
    @Schema(description = "Описание транзакции", example = "Оплата покупок")
    private String description;

    @NotNull
    @Schema(description = "Тип транзакции", example = "DEPOSIT")
    private TransactionType type;

}
