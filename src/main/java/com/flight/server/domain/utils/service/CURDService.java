package com.flight.server.domain.utils.service;

public interface CURDService<T,K> {
    T create(T t);
    T update(T t);
    T getById(K id);
    void delete(T t);
}
