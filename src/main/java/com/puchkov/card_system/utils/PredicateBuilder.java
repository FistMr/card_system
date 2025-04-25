package com.puchkov.card_system.utils;

import com.puchkov.card_system.dto.CardFilter;
import com.puchkov.card_system.dto.Filter;
import com.puchkov.card_system.dto.TransactionFilter;
import com.puchkov.card_system.entity.Card;
import com.puchkov.card_system.entity.Transaction;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PredicateBuilder {

    private List<Predicate> buildCardPredicates(CardFilter filter, CriteriaBuilder cb, Root<?> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getCardStatus() != null) {
            predicates.add(cb.equal(root.get("status"), filter.getCardStatus()));
        }
        if (filter.getBalance() != null) {
            predicates.add(cb.gt(root.get("balance"), filter.getBalance()));
        }
        if (filter.getExpirationDate() != null) {
            LocalDate expirationDate = LocalDate.parse(filter.getExpirationDate().toString());
            predicates.add(cb.equal(root.get("expirationDate"), expirationDate));
        }
        if (filter.getOwnerNameSurname() != null && !filter.getOwnerNameSurname().isBlank()) {
            predicates.add(cb.like(root.get("owner").get("nameSurname"), filter.getOwnerNameSurname()));
        }
        if (filter.getOwnerId() != null && filter.getOwnerId() != 0) {
            predicates.add(cb.equal(root.get("owner").get("id"), filter.getOwnerId()));
        }
        return predicates;
    }

    private List<Predicate> buildTransactionPredicates(TransactionFilter filter, CriteriaBuilder cb, Root<?> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getId() != null && filter.getId() != 0) {
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if (filter.getCardId() != null) {
            Join<Transaction, Card> cardJoin = root.join("card", JoinType.INNER);
            predicates.add(cb.equal(cardJoin.get("id"), filter.getCardId()));
        }
        if (filter.getAmount() != null) {
            predicates.add(cb.gt(root.get("amount"), filter.getAmount()));
        }
        if (filter.getTimestamp() != null) {
            predicates.add(cb.equal(root.get("timestamp"), filter.getTimestamp()));
        }
        if (filter.getType() != null && filter.getType().toString().isBlank()) {
            predicates.add(cb.like(root.get("type"), filter.getType().toString()));
        }
        return predicates;
    }

    public List<Predicate> buildPredicates(Filter filter, CriteriaBuilder cb, Root<?> root) {
        if (filter instanceof CardFilter) {
            return buildCardPredicates((CardFilter) filter, cb, root);
        }
        if (filter instanceof TransactionFilter) {
            return buildTransactionPredicates((TransactionFilter) filter, cb, root);
        }
        throw new IllegalArgumentException("Неподдерживаемый тип фильтра: " + filter.getClass());
    }
}
