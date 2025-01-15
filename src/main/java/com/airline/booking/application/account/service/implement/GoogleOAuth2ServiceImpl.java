package com.airline.booking.application.account.service.implement;

import com.airline.booking.application.account.dataTransferObject.AccountDTO;
import com.airline.booking.application.account.dataTransferObject.GoogleAuth2DTO;
import com.airline.booking.application.account.dataTransferObject.request.OAuth2LoginRequest;
import com.airline.booking.application.account.service.IAccountUseCase;
import com.airline.booking.application.account.service.IOAuth2Service;
import com.airline.booking.application.account.type.OAuth2Type;
import com.airline.booking.infrastructure.exception.ApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Service
public class GoogleOAuth2ServiceImpl implements IOAuth2Service {
    private static final Logger logger = LoggerFactory.getLogger(GoogleOAuth2ServiceImpl.class);
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.google.scope}")
    private String scope;
    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String userInfoUri;

    private final IAccountUseCase accountUseCase;

    public GoogleOAuth2ServiceImpl(IAccountUseCase accountUseCase) {
        this.accountUseCase = accountUseCase;
    }

    @Override
    public String generateAuthUrl(OAuth2Type type) {
        logger.info("generateAuthUrl {}", type);
        List<String> scopes = List.of(scope.split(","));
        GoogleAuthorizationCodeRequestUrl requestUrl = new GoogleAuthorizationCodeRequestUrl(
                clientId,
                clientSecret,
                scopes
        );
        return requestUrl.build();
    }

    @Override
    public AccountDTO authenticate(OAuth2Type type, String code) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        try {
            NetHttpTransport httpTransport = new NetHttpTransport();
            GsonFactory gsonFactory = new GsonFactory();
            GoogleTokenResponse token = new GoogleAuthorizationCodeTokenRequest(
                    httpTransport, gsonFactory,
                    clientId, clientSecret,
                    code,
                    redirectUri
            ).execute();
            String accessToken = token.getAccessToken();

            restTemplate.getInterceptors().add((req, body, executionContext) -> {
                req.getHeaders().set("Authorization", "Bearer " + accessToken);
                return executionContext.execute(req, body);
            });
            ResponseEntity<String> response = restTemplate.getForEntity(userInfoUri, String.class);
            GoogleAuth2DTO googleAuth2DTO = new ObjectMapper().readValue(response.getBody(), GoogleAuth2DTO.class);
            OAuth2LoginRequest oAuth2LoginRequest = new OAuth2LoginRequest(
                    type.getValue(),
                    googleAuth2DTO.getSub(),
                    googleAuth2DTO.getFirstName(),
                    googleAuth2DTO.getLastName(),
                    googleAuth2DTO.getEmail(),
                    null,
                    googleAuth2DTO.getDateOfBirth(),
                    googleAuth2DTO.getGender()
            );
            return accountUseCase.loginWithOAuth2(oAuth2LoginRequest);
        } catch (Exception e) {
            throw new ApplicationException("");
        }
    }
}
