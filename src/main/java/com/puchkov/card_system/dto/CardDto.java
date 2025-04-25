package com.puchkov.card_system.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.puchkov.card_system.utils.CardNumberMask;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CardDto {

    private Long id;

    @JsonSerialize(using = CardNumberMask.class)
    private String maskedCardNumber;

    private String ownerName;

    private LocalDate expirationDate;

    private String status;

    private BigDecimal balance;

    private BigDecimal dailyWithdrawalLimit;

    private BigDecimal monthlyWithdrawalLimit;
}
