package com.airline.booking.application.cart.service;

import com.airline.booking.domain.cart.repository.CartRepository;

public class ICartUseCase {
    private final CartRepository cartRepository;
    public ICartUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

}
