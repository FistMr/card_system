package com.puchkov.card_system.repository;

import com.puchkov.card_system.dto.Filter;
import com.puchkov.card_system.entity.Card;
import com.puchkov.card_system.entity.Transaction;
import com.puchkov.card_system.utils.PredicateBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class FilterTransactionRepositoryImpl implements FilterTransactionRepository {
    private final EntityManager entityManager;
    private final PredicateBuilder predicateBuilder;

    @Override
    public Page<Transaction> findAllByFilter(Filter filter, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Transaction> criteria = cb.createQuery(Transaction.class);
        Root<Transaction> root = criteria.from(Transaction.class);
        criteria.select(root);

        List<Predicate> predicates = predicateBuilder.buildPredicates(filter, cb, root);
        criteria.where(predicates.toArray(Predicate[]::new));

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Transaction> countRoot = countQuery.from(Transaction.class);
        countQuery.select(cb.count(countRoot));
        countQuery.where(predicateBuilder.buildPredicates(filter, cb, countRoot).toArray(Predicate[]::new));
        criteria.where(predicates.toArray(Predicate[]::new));

        List<Transaction> result = entityManager.createQuery(criteria)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(result, pageable, total);
    }
}
