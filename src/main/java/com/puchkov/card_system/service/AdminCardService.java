package com.puchkov.card_system.service;

import com.puchkov.card_system.dto.*;
import com.puchkov.card_system.entity.Card;
import com.puchkov.card_system.entity.Transaction;
import com.puchkov.card_system.entity.User;
import com.puchkov.card_system.mapper.CardMapper;
import com.puchkov.card_system.mapper.TransactionMapper;
import com.puchkov.card_system.repository.CardRepository;
import com.puchkov.card_system.repository.FilterCardRepository;
import com.puchkov.card_system.repository.FilterTransactionRepository;
import com.puchkov.card_system.utils.CardNumberGenerator;
import com.puchkov.card_system.utils.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCardService {

    private final UserService userService;
    private final CardRepository cardRepository;
    private final FilterCardRepository filterCardRepository;
    private final FilterTransactionRepository filterTransactionRepository;
    private final CardMapper cardMapper;
    private final TransactionMapper transactionMapper;
    private final Encryptor encryptor;


    public PageResponse<CardDto> findAll(CardFilter filter, Pageable pageable) {
        Page<Card> page = filterCardRepository.findAllByFilter(filter, pageable);

        List<CardDto> content = page.getContent().stream()
                .map(cardMapper::fromEntity)
                .toList();

        return new PageResponse<>(
                content,
                new PageResponse.Metadata(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        page.getTotalElements()
                )
        );
    }

    public PageResponse<TransactionDto> findAllTransaction(TransactionFilter filter, Pageable pageable) {
        Page<Transaction> page = filterTransactionRepository.findAllByFilter(filter, pageable);

        List<TransactionDto> content = page.getContent().stream()
                .map(transactionMapper::fromEntity)
                .toList();

        return new PageResponse<>(
                content,
                new PageResponse.Metadata(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        page.getTotalElements()
                )
        );
    }

    public ResponseEntity<?> updateLimits(CardLimitsDto cardLimitsDto) {
        Card card = cardRepository.findById(cardLimitsDto.getId()).orElseThrow(
                () -> new IllegalArgumentException("Карта с таким Id не найдена: " + cardLimitsDto.getId()));
        card.setDailyWithdrawalLimit(cardLimitsDto.getDailyLimit());
        card.setMonthlyWithdrawalLimit(cardLimitsDto.getMonthlyLimit());
        card = cardRepository.save(card);
        return ResponseEntity.ok(cardMapper.fromEntity(card));
    }

    public ResponseEntity<?> createNewCard(NewCardDto newCardDto) {
        User user = userService.findByNameSurname(newCardDto.getOwnerNameSurname()).orElseThrow(
                () -> new IllegalArgumentException("Пользователь не найден: " + newCardDto.getOwnerNameSurname()));
        Card card = new Card(encryptor.encrypt(CardNumberGenerator.generate()), user);
        card = cardRepository.save(card);
        return ResponseEntity.ok(cardMapper.fromEntity(card));
    }

    public ResponseEntity<?> setStatus(NewCardStatusDto newCardStatusDto) {
        Card card = cardRepository.findById(newCardStatusDto.getId()).orElseThrow(
                () -> new IllegalArgumentException("Карта с таким Id не найдена: " + newCardStatusDto.getId()));
        card.setStatus(newCardStatusDto.getAction());
        cardRepository.save(card);
        return ResponseEntity.ok(cardMapper.fromEntity(card));
    }

    public void deleteCard(Long id) {
        Card card = cardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Карта с таким Id не найдена: " + id));
        cardRepository.delete(card);
    }
}