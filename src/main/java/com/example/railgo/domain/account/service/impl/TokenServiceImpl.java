package com.example.railgo.domain.account.service.impl;

import com.example.railgo.domain.account.exception.AccountExceptionCode;
import com.example.railgo.domain.account.model.Token;
import com.example.railgo.domain.account.model.User;
import com.example.railgo.domain.account.repository.TokenRepository;
import com.example.railgo.domain.account.service.ITokenService;
import com.example.railgo.domain.utils.exception.BusinessException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenServiceImpl implements ITokenService {
    private final TokenRepository tokenRepository;

    @Value("${spring.jwt.expiration}")
    private long expiration;
    @Value("${spring.jwt.expiration-refresh-token}")
    private long expirationRefreshToken;
    @Value("${spring.jwt.secretKeyAccess}")
    private String secretKeyAccess;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }
    @Override
    public Token generateToken(User user) {
        String accountId = user.getId();
        String userName = user.getPhoneNumber();
        Map<String, Object> claims = buildClaims(accountId, userName);

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiresAt = new Date(issuedAt.getTime() + expiration*1000);

        String accessToken = generateToken(claims, userName, expiresAt);

        String refreshToken = generateRefreshToken();
        Date refreshExpiresAt = new Date(issuedAt.getTime() + expirationRefreshToken);

        Token newToken = new Token(accountId,accessToken,issuedAt,expiresAt,refreshToken,refreshExpiresAt);
        return tokenRepository.save(newToken);
    }
    private Map<String,Object> buildClaims(String accountId,
                                           String subject){
        Map<String, Object> claims = new HashMap<>();
        claims.put("accountId", accountId);
        claims.put("subject", subject);
        return claims;
    }
    private String generateToken(Map<String, Object> claims,
                                 String subject,
                                 Date expiresAt) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .expiration(expiresAt)
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }
    private SecretKey getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKeyAccess);
        return Keys.hmacShaKeyFor(bytes);
    }
    private String generateRefreshToken(){
        return UUID.randomUUID().toString();
    }
    @Override
    public String authenticate(String accessToken) {
        Token existToken = tokenRepository.findByValue(accessToken)
                .orElseThrow(() -> new BusinessException(AccountExceptionCode.INVALID_TOKEN));
        if (existToken.getExpiresAt().before(new Date())) {
            throw new BusinessException(AccountExceptionCode.UNAUTHORIZED_ACCESS);
        }
        try {
            var claims = Jwts.parser()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new BusinessException(AccountExceptionCode.UNAUTHORIZED_ACCESS, "JWT token has expired.");
        } catch (JwtException e) {
            throw new BusinessException(AccountExceptionCode.INVALID_TOKEN, "Invalid JWT token.");
        } catch (Exception e) {
            throw new BusinessException(AccountExceptionCode.UNAUTHORIZED_ACCESS, "Unauthorized access.");
        }
    }
}
