package com.airline.booking.application.account.service;

import com.airline.booking.application.account.dataTransferObject.AccountDTO;
import com.airline.booking.application.account.type.OAuth2Type;

public interface IOAuth2Service {
    String generateAuthUrl(OAuth2Type type);
    AccountDTO authenticate(OAuth2Type type, String code);
}
