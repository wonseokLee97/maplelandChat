package com.mapleland.chatservice.shared.jwt;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final String token;
    private TokenClaims claims;

    // 인증 전
    public JwtAuthenticationToken(String token) {
        super(null);
        this.token = token;
        setAuthenticated(false);
    }

    // 인증 후
    public JwtAuthenticationToken(TokenClaims claims, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = null;
        this.claims = claims;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return claims;
    }
}
