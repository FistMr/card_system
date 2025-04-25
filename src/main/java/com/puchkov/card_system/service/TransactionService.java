package com.puchkov.card_system.service;

import com.puchkov.card_system.dto.NewTransactionDto;
import com.puchkov.card_system.dto.PageResponse;
import com.puchkov.card_system.dto.TransactionDto;
import com.puchkov.card_system.dto.TransactionFilter;
import com.puchkov.card_system.entity.Card;
import com.puchkov.card_system.entity.Transaction;
import com.puchkov.card_system.entity.User;
import com.puchkov.card_system.mapper.TransactionMapper;
import com.puchkov.card_system.repository.CardRepository;
import com.puchkov.card_system.repository.TransactionRepository;
import com.puchkov.card_system.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;
    private final TransactionMapper transactionMapper;
    private final CheckService checkService;
    private final AdminCardService adminCardService;

    @Transactional
    public ResponseEntity<?> newTransaction(NewTransactionDto newTransactionDto, HttpServletRequest request) {
        Long userId = jwtTokenUtils.getId(request);
        User user = userService.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Пользователь не найден: " + userId)
        );
        checkService.checkAvailability(user, newTransactionDto.getCardId());

        Card card = cardRepository.findById(newTransactionDto.getCardId()).get();

        checkService.checkStatusCard(card);
        checkService.checkEnoughFunds(card, newTransactionDto.getAmount());
        checkService.checkLimitNotExceeded(card, newTransactionDto.getAmount());

        Transaction transaction = transactionMapper.toEntity(card, newTransactionDto);
        card.setBalance(card.getBalance().subtract(newTransactionDto.getAmount()));

        cardRepository.save(card);
        transaction = transactionRepository.save(transaction);
        return ResponseEntity.ok(transactionMapper.fromEntity(transaction));
    }

    public PageResponse<TransactionDto> getUserTransactions(HttpServletRequest request, Pageable pageable, TransactionFilter transactionFilter, Long cardId) {
        Long userId = jwtTokenUtils.getId(request);
        User user = userService.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Пользователь не найден: " + userId)
        );
        checkService.checkAvailability(user, cardId);
        transactionFilter.setCardId(cardId);
        return adminCardService.findAllTransaction(transactionFilter, pageable);
    }
}
