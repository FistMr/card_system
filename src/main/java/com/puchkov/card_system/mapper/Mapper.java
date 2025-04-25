package com.puchkov.card_system.mapper;

public interface Mapper<F, T> {
    T fromEntity(F object);
}
