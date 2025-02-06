package com.airline.booking.application.ticket.service;

import com.airline.booking.application.ticket.dataTransferObject.response.CartResponse;

public interface ICartUseCase {
    CartResponse getCart(String sessionId);
    void addItemToCart(String sessionId, String itemId);
    Integer getCartItemCount(String sessionId);
}
