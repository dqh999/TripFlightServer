package com.airline.booking.application.ticket.service.impl;

import com.airline.booking.application.ticket.dataTransferObject.response.CartResponse;
import com.airline.booking.application.ticket.dataTransferObject.response.TicketConfirmResponse;
import com.airline.booking.application.ticket.service.ICartUseCase;
import com.airline.booking.domain.ticket.model.Cart;
import com.airline.booking.infrastructure.service.cache.CacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartUseCaseImpl implements ICartUseCase {
    private final CacheService cacheService;

    public CartUseCaseImpl(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Value("${cart.key}")
    private String cartKey;

    @Override
    public void addItemToCart(String sessionId, String itemId) {
        Cart cart = getCartBySessionId(sessionId);
        cart.addTicket(itemId);
    }

    private Cart getCartBySessionId(String sessionId) {
        Cart existingCart = cacheService.get(cartKey.formatted(sessionId), Cart.class);
        if (existingCart != null) return existingCart;

        Cart newCart = new Cart(
                null,
                sessionId,
                0,
                null
        );
        cacheService.put(cartKey, newCart);
        return newCart;
    }

    @Override
    public CartResponse getCart(String sessionId) {
        Cart cart = getCartBySessionId(sessionId);
        List<TicketConfirmResponse> ticketResponses = cart.getTicketIds().stream().map(ticketId ->
                cacheService.get(ticketId, TicketConfirmResponse.class)
        ).collect(Collectors.toList());

        return new CartResponse(
                cart.getId(),
                cart.getSessionId(),
                cart.getQuantity(),
                ticketResponses
        );
    }

    @Override
    public Integer getCartItemCount(String sessionId) {
        Cart cart = getCartBySessionId(sessionId);
        return cart.getQuantity();
    }

}
