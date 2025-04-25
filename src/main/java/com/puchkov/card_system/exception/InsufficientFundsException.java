package com.puchkov.card_system.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(BigDecimal currentBalance, BigDecimal requiredAmount) {
        super(String.format(
                "Недостаточно средств. Текущий баланс: %s, требуется: %s",
                currentBalance, requiredAmount
        ));
    }
}
