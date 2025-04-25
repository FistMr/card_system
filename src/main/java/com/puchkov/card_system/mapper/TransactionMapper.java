package com.puchkov.card_system.mapper;

import com.puchkov.card_system.dto.NewTransactionDto;
import com.puchkov.card_system.dto.TransactionDto;
import com.puchkov.card_system.dto.TransferRequestDto;
import com.puchkov.card_system.entity.Card;
import com.puchkov.card_system.entity.Transaction;
import com.puchkov.card_system.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class TransactionMapper implements Mapper<Transaction, TransactionDto> {
    @Override
    public TransactionDto fromEntity(Transaction object) {
        return TransactionDto.builder()
                .id(object.getId())
                .cardId(object.getCard().getId())
                .amount(object.getAmount())
                .description(object.getDescription())
                .timestamp(object.getTimestamp())
                .type(object.getType().toString())
                .build();
    }

    public Transaction toEntity(Card card, NewTransactionDto newTransactionDto) {
        return Transaction.builder()
                .card(card)
                .amount(newTransactionDto.getAmount().multiply(BigDecimal.valueOf(-1)))
                .description(newTransactionDto.getDescription())
                .timestamp(LocalDateTime.now())
                .type(newTransactionDto.getType())
                .build();
    }

    public Transaction toEntity(Card card, TransferRequestDto transferRequestDto, boolean isAdd) {
        return Transaction.builder()
                .card(card)
                .amount(isAdd ? transferRequestDto.getAmount() : transferRequestDto.getAmount().negate())
                .description(transferRequestDto.getDescription())
                .timestamp(LocalDateTime.now())
                .type(TransactionType.TRANSFER)
                .build();
    }
}
