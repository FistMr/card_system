package com.puchkov.card_system.repository;

import com.puchkov.card_system.dto.Filter;
import com.puchkov.card_system.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterCardRepository {
    Page<Card> findAllByFilter(Filter filter, Pageable pageable);
}
