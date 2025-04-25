package com.puchkov.card_system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.puchkov.card_system.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionFilter implements Filter {
    @Schema(description = "Фильтрация по id транзакции", example = "1")
    private Long id;

    @Schema(description = "Фильтрация по id карты", example = "1")
    private Long cardId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @DecimalMin(value = "0.0", inclusive = false)
    @Schema(description = "Фильтрация по сумме транзакции", example = "1500")
    private BigDecimal amount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Фильтрация по времени совершения операции", example = "2030-01-01")
    private LocalDateTime timestamp;

    @Schema(description = "Фильтрация по типу транзакции", example = "DEPOSIT")
    @Enumerated(EnumType.STRING)
    private TransactionType type;
}
