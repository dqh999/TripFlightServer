package com.example.railgo.domain.account.model;


import com.example.railgo.domain.utils.valueObject.Id;

import java.util.Date;

public class Token {
    private Id id;
    private String value;
    private Date issuedAt;
    private Date expiresAt;
    private String refreshToken;
    private Date refreshTokenExpiresAt;

    public Token() {
    }

    public Token(String value, Date issuedAt, Date expiresAt, String refreshToken, Date refreshTokenExpiresAt) {
        this.id = new Id();
        this.value = value;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }

    public Token(String id, String value, Date issuedAt, Date expiresAt, String refreshToken, Date refreshTokenExpiresAt) {
        this.id = new Id(id);
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

