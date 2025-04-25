package com.puchkov.card_system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.puchkov.card_system.enums.CardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CardFilter implements Filter {
    @Schema(description = "Фильтрация по Имени и Фамилии пользователя", example = "Ivan Ivanov")
    private String ownerNameSurname;

    @Schema(description = "Срок Действия", example = "2030-04-24")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate expirationDate;

    @Schema(description = "Статус карты", example = "ACTIVE")
    private CardStatus cardStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal balance;

    @Schema(description = "Id пользователя", example = "1")
    private Long ownerId;
}
