package com.puchkov.card_system.exception;

import com.puchkov.card_system.dto.CardDto;

public class CardBlockedException extends RuntimeException {
    public CardBlockedException(CardDto cardDto) {
        super(String.format(
                "Данная карта заблокирована или закончился срок эксплуатации: %s",
                cardDto
        ));
    }
}
