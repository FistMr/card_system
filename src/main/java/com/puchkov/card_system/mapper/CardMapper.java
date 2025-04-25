package com.puchkov.card_system.mapper;

import com.puchkov.card_system.dto.CardDto;
import com.puchkov.card_system.entity.Card;
import com.puchkov.card_system.utils.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardMapper implements Mapper<Card, CardDto> {
    private final Encryptor encryptor;

    @Override
    public CardDto fromEntity(Card object) {
        return CardDto.builder()
                .id(object.getId())
                .maskedCardNumber(encryptor.decrypt(object.getCardNumber()))
                .ownerName(object.getOwner().getNameSurname())
                .expirationDate(object.getExpirationDate())
                .status(object.getStatus().toString())
                .balance(object.getBalance())
                .dailyWithdrawalLimit(object.getDailyWithdrawalLimit())
                .monthlyWithdrawalLimit(object.getMonthlyWithdrawalLimit())
                .build();
    }
}
