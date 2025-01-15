package com.airline.booking.infrastructure.security;

import com.airline.booking.application.account.service.IAccountUseCase;
import com.airline.booking.domain.exception.BusinessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final IAccountUseCase accountUseCase;
    private final HandlerExceptionResolver resolver;


    @Autowired
    public AuthenticationFilter(IAccountUseCase accountUseCase,
                                @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.accountUseCase = accountUseCase;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String accessToken = authHeader.substring(7);
            try {
                UserDetail userDetail = accountUseCase.authenticate(accessToken);
            if (userDetail != null) {
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(userDetail,
                                null,
                                userDetail.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            } catch (BusinessException e) {
                resolver.resolveException(request, response, null, e);
            }

        }

        filterChain.doFilter(request, response);
    }
}
