package com.example.railgo.application.security;

import com.example.railgo.application.account.service.implement.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailServiceImpl userDetailService;
    @Autowired
    public AuthenticationFilter(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            UserDetails userDetail = userDetailService.authenticate(accessToken);
            filterChain.doFilter(request, response);
        }
        filterChain.doFilter(request, response);
    }
}
