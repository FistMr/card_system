package com.puchkov.card_system.service;

import com.puchkov.card_system.dto.*;
import com.puchkov.card_system.entity.Card;
import com.puchkov.card_system.entity.User;
import com.puchkov.card_system.enums.CardStatus;
import com.puchkov.card_system.mapper.TransactionMapper;
import com.puchkov.card_system.repository.CardRepository;
import com.puchkov.card_system.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserCardService {
    private final AdminCardService adminCardService;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final CardRepository cardRepository;
    private final CheckService checkService;
    private final TransactionMapper transactionMapper;

    public PageResponse<CardDto> getUserCards(HttpServletRequest request, Pageable pageable) {
        Long userId = jwtTokenUtils.getId(request);
        return adminCardService.findAll(
                CardFilter.builder().ownerId(userId).build(), pageable);
    }

    public ResponseEntity<?> blockCard(Long cardId, HttpServletRequest request) {
        Long userId = jwtTokenUtils.getId(request);
        User user = userService.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Пользователь не найден: " + userId)
        );
        checkService.checkAvailability(user, cardId);
        return adminCardService.setStatus(new NewCardStatusDto(cardId, CardStatus.PENDING_BLOCK));
    }
    @Transactional
    public String transfer(HttpServletRequest request, TransferRequestDto transferRequestDto) {
        Long userId = jwtTokenUtils.getId(request);
        User user = userService.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Пользователь не найден: " + userId)
        );

        checkService.checkAvailability(user, transferRequestDto.getFromCardId());
        checkService.checkAvailability(user, transferRequestDto.getToCardId());

        Card fromCard = cardRepository.findById(transferRequestDto.getFromCardId()).get();
        Card toCard = cardRepository.findById(transferRequestDto.getToCardId()).get();

        checkService.checkStatusCard(fromCard);
        checkService.checkStatusCard(toCard);
        checkService.checkEnoughFunds(fromCard, transferRequestDto.getAmount());
        checkService.checkLimitNotExceeded(fromCard, transferRequestDto.getAmount());
        checkService.checkLimitNotExceeded(toCard, transferRequestDto.getAmount());

        fromCard.setBalance(fromCard.getBalance().subtract(transferRequestDto.getAmount()));
        fromCard.getTransactions().add(transactionMapper.toEntity(fromCard, transferRequestDto, false));
        toCard.setBalance(toCard.getBalance().add(transferRequestDto.getAmount()));
        toCard.getTransactions().add(transactionMapper.toEntity(toCard, transferRequestDto, true));

        cardRepository.save(fromCard);
        cardRepository.save(toCard);
        return HttpStatus.ACCEPTED.toString();
    }
}
