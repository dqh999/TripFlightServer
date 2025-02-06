package com.airline.booking.domain.cart.repository;

import com.airline.booking.domain.cart.model.Cart;

public interface CartRepository {
    Cart findByIpAddress(String ipAddress);
    Cart findByUserId(String userId);
}
