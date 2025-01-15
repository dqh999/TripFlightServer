package com.airline.booking.domain.account.model;

import com.airline.booking.domain.utils.model.BaseModel;
import com.airline.booking.domain.utils.valueObject.Id;

import java.util.Date;

public class Token extends BaseModel {
    private Id id;
    private String accountId;
    private String value;
    private Date issuedAt;
    private Date expiresAt;
    private Boolean isRevoked;
    private String refreshToken;
    private Date refreshTokenExpiresAt;

    public Token() {
    }

    public Token(String accountId,String value, Date issuedAt, Date expiresAt, Boolean isRevoked, String refreshToken, Date refreshTokenExpiresAt) {
        this.id = new Id();
        this.accountId = accountId;
        this.value = value;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.isRevoked = isRevoked;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }

    public Token(String id,String accountId, String value, Date issuedAt, Date expiresAt, Boolean isRevoked, String refreshToken, Date refreshTokenExpiresAt) {
        this.id = new Id(id);
        this.accountId = accountId;
        this.value = value;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }

    public String getId() {
        return this.id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Boolean getRevoked() {
        return isRevoked;
    }

    public void setRevoked(Boolean revoked) {
        isRevoked = revoked;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getRefreshTokenExpiresAt() {
        return refreshTokenExpiresAt;
    }

    public void setRefreshTokenExpiresAt(Date refreshTokenExpiresAt) {
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }
}

