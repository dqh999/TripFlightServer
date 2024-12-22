package com.railgo.domain.utils.valueObject;

import com.railgo.domain.utils.exception.BusinessException;
import com.railgo.domain.utils.service.CurrencyConverter;
import com.railgo.domain.utils.type.Currency;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
    private final BigDecimal value;
    private final Currency currency;


    public Money(BigDecimal value, String currency) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Price cannot be null or negative.");
        }
        if (currency == null || currency.isEmpty()) {
            throw new BusinessException("Currency cannot be null or empty.");
        }
        this.value = value;
        this.currency = Currency.valueOf(currency);
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrency() {
        return currency.getValue();
    }


    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            other = other.convertTo(this);
        }
        return new Money(this.value.add(other.value), this.currency.getValue());
    }
    private Money convertTo(Money target) {
        if (this.currency.equals(target.currency)) {
            return this;
        }

        BigDecimal exchangeRate = CurrencyConverter.getExchangeRate(this.currency, target.currency);
        BigDecimal convertedValue = this.value.multiply(exchangeRate);
        return new Money(convertedValue, target.currency.getValue());
    }
    public Money subtract(Money other) {
        if (!this.currency.equals(other.currency)) {
            other = other.convertTo(this);
        }
        return new Money(this.value.subtract(other.value), this.currency.getValue());
    }
}
