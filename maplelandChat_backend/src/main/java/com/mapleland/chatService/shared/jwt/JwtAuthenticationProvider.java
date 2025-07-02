package com.mapleland.chatService.shared.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtProvider jwtProvider;

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (!supports(authentication.getClass())) {
            throw new IllegalArgumentException("Unsupported authentication type");
        }

        JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
        String token = (String) jwtAuthentication.getCredentials();
        TokenClaims claims = jwtProvider.validateToken(token);

        return new JwtAuthenticationToken(claims, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}