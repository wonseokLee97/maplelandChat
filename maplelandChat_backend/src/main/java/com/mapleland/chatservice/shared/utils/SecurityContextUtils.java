package com.mapleland.chatservice.shared.utils;

import com.mapleland.chatservice.shared.jwt.TokenClaims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityContextUtils {

    // 사용자 TokenClaims
    public String getUUID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TokenClaims principal = (TokenClaims) authentication.getPrincipal();
        return principal.getId();
    }
}
