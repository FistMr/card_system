package com.puchkov.card_system.service;

import com.puchkov.card_system.entity.Card;
import com.puchkov.card_system.entity.Transaction;
import com.puchkov.card_system.entity.User;
import com.puchkov.card_system.enums.CardStatus;
import com.puchkov.card_system.exception.CardBlockedException;
import com.puchkov.card_system.exception.ExceededLimitException;
import com.puchkov.card_system.exception.InsufficientFundsException;
import com.puchkov.card_system.mapper.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckService {

    private  final CardMapper cardMapper;

    public void checkAvailability(User user, Long cardId) {
        if (user.getCards().stream().map(Card::getId).noneMatch(id -> id.equals(cardId))) {
            throw new IllegalArgumentException("Вы не владеете такой картой id: " + cardId);
        }
    }

    public void checkStatusCard(Card card) {
        if (card.getStatus().equals(CardStatus.BLOCKED) || card.getStatus().equals(CardStatus.EXPIRED)) {
            throw new CardBlockedException(cardMapper.fromEntity(card));
        }
    }

    public void checkEnoughFunds(Card card, BigDecimal target) {
        if (card.getBalance().compareTo(target) < 0) {
            throw new InsufficientFundsException(card.getBalance(), target);
        }
    }

    public void checkLimitNotExceeded(Card card, BigDecimal target) {
        String today = LocalDateTime.now().toString().substring(0, 9);
        String toMonth = LocalDateTime.now().toString().substring(0, 7);
        BigDecimal dailySumTransaction = card.getTransactions().stream()
                .filter(e -> e.getTimestamp().toString().contains(today))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal monthSumTransaction = card.getTransactions().stream()
                .filter(e -> e.getTimestamp().toString().contains(toMonth))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (dailySumTransaction.add(target).compareTo(card.getDailyWithdrawalLimit()) > 0
                || monthSumTransaction.add(target).compareTo(card.getMonthlyWithdrawalLimit()) > 0) {
            throw new ExceededLimitException(dailySumTransaction, monthSumTransaction, cardMapper.fromEntity(card));
        }
    }
}
