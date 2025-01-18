package com.airline.booking.presentation.account.operations;

import com.airline.booking.application.account.dataTransferObject.AccountDTO;
import com.airline.booking.application.account.type.OAuth2Type;
import com.airline.booking.infrastructure.exception.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface OAuth2Operations {
    @GetMapping("/oauth2")
    ResponseEntity<ApiResponse<String>> generateOAuth2LoginUrl(@RequestParam("type") OAuth2Type type);

    @GetMapping("/login/oauth2/code/google")
    ResponseEntity<ApiResponse<AccountDTO>> processOAuth2Callback(
            @RequestParam("type") OAuth2Type type,
            @RequestParam("code") String code
    );
}
