package com.airline.booking.presentation.cart;

import com.airline.booking.application.ticket.dataTransferObject.response.CartResponse;
import com.airline.booking.application.ticket.service.ICartUseCase;
import com.airline.booking.application.utils.RequestUtil;
import com.airline.booking.infrastructure.exception.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix/cart}")
public class CartController {
    private final ICartUseCase cartUseCase;

    public CartController(ICartUseCase cartUseCase) {
        this.cartUseCase = cartUseCase;
    }

    @GetMapping("/quantity")
    public ResponseEntity<ApiResponse<Integer>> handleGetCartQuantity(HttpServletRequest request) {
        var sessionId = RequestUtil.getSessionId(request);
        var res = cartUseCase.getCartItemCount(sessionId);
        return ApiResponse.<Integer>build()
                .withData(res)
                .toEntity();
    }

    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<CartResponse>> handleGetCartDetail(HttpServletRequest httpServletRequest) {
        var sessionId = RequestUtil.getSessionId(httpServletRequest);
        var res = cartUseCase.getCart(sessionId);
        return ApiResponse.<CartResponse>build()
                .withData(res)
                .toEntity();
    }
}
