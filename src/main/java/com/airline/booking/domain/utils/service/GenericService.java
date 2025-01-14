package com.airline.booking.domain.utils.service;

public interface GenericService<T,K> {
    T create(T t);
    T update(T t);
    T getById(K id);
    void delete(T t);
}
