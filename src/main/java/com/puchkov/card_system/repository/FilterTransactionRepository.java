package com.puchkov.card_system.repository;

import com.puchkov.card_system.dto.Filter;
import com.puchkov.card_system.entity.Card;
import com.puchkov.card_system.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterTransactionRepository {
    Page<Transaction> findAllByFilter(Filter filter, Pageable pageable);
}
