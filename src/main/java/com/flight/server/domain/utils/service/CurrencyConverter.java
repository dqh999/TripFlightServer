package com.flight.server.domain.utils.service;

import com.flight.server.domain.utils.type.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverter {

    public static BigDecimal getExchangeRate(Currency from, Currency to) {
        if (from == Currency.USD && to == Currency.VND) {
            return BigDecimal.valueOf(23000);
        } else if (from == Currency.VND && to == Currency.USD) {
            return BigDecimal.valueOf(1).divide(BigDecimal.valueOf(23000), 6, RoundingMode.HALF_UP);
        }
        return BigDecimal.ONE;
    }
}

