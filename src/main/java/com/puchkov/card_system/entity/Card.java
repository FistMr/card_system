package com.puchkov.card_system.entity;

import com.puchkov.card_system.enums.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "cards")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cardNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus status;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Builder.Default
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    @Column(name = "daily_withdrawal_limit", precision = 19, scale = 2)
    private BigDecimal dailyWithdrawalLimit;

    @Column(name = "monthly_withdrawal_limit", precision = 19, scale = 2)
    private BigDecimal monthlyWithdrawalLimit;


    public Card(String cardNumber, User owner) {
        this.cardNumber = cardNumber;
        this.owner = owner;
        this.expirationDate = LocalDate.now().plusYears(5);
        this.status = CardStatus.ACTIVE;
        this.balance = BigDecimal.ZERO;
        this.dailyWithdrawalLimit = BigDecimal.valueOf(150000.00);
        this.monthlyWithdrawalLimit = BigDecimal.valueOf(1500000.00);
    }
}