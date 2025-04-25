package com.puchkov.card_system.exception;

import com.puchkov.card_system.dto.CardDto;

import java.math.BigDecimal;

public class ExceededLimitException extends RuntimeException {
    public ExceededLimitException(BigDecimal dailySumTransaction, BigDecimal monthSumTransaction,CardDto cardDto) {
        super(String.format("Превышен один из лимитов." +
                        "Сумма операций: за сегодня: %s, за месяц: %s " +
                        "Лимиты: дневной: %s , месячный: %s",
                dailySumTransaction, monthSumTransaction, cardDto.getDailyWithdrawalLimit(), cardDto.getMonthlyWithdrawalLimit()
        ));
    }
}
