package com.airline.booking.infrastructure.persistence.cart.repository;

import com.airline.booking.domain.cart.model.Cart;
import com.airline.booking.domain.cart.repository.CartRepository;
import com.airline.booking.infrastructure.service.cache.CacheService;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepositoryImpl implements CartRepository {
    private final CacheService cacheService;

    public CartRepositoryImpl(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public Cart findByIpAddress(String ipAddress) {
        return null;
    }

    @Override
    public Cart findByUserId(String userId) {
        return null;
    }
}
