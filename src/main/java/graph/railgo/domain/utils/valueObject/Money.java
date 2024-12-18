package graph.railgo.domain.utils.valueObject;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
    private final BigDecimal value; // Giá trị tiền tệ
    private final String currency;  // Đơn vị tiền tệ (USD, EUR, VND, ...)

    public Money(BigDecimal value, String currency) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be null or negative.");
        }
        if (currency == null || currency.isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be null or empty.");
        }
        this.value = value;
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Money price = (Money) obj;
        return value.equals(price.value) && currency.equals(price.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

    @Override
    public String toString() {
        return value + " " + currency;
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot add prices with different currencies.");
        }
        return new Money(this.value.add(other.value), this.currency);
    }

    public Money subtract(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot subtract prices with different currencies.");
        }
        return new Money(this.value.subtract(other.value), this.currency);
    }
}
