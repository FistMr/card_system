package com.puchkov.card_system.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDto {
    private Long id;
    private Long cardId;
    private BigDecimal amount;
    private String description;
    private LocalDateTime timestamp;
    private String type;
}
