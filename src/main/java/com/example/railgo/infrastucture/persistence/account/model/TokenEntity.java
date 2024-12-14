package com.example.railgo.infrastucture.persistence.account.model;

import com.example.railgo.infrastucture.persistence.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "tbl_user_tokens")
public class TokenEntity extends BaseEntity {
    @Id
    private String id;
    private String value;
    @Column(name = "issued_at")
    private Date issuedAt;
    @Column(name = "expires_at")
    private Date expiresAt;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "refresh_token_expires_at")
    private Date refreshTokenExpiresAt;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "user_agent")
    private String userAgent;

    public TokenEntity() {
    }

    public TokenEntity(String id, String value, Date issuedAt, Date expiresAt, String refreshToken, Date refreshTokenExpiresAt, String ipAddress, String userAgent) {
        this.id = id;
        this.value = value;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
