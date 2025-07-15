package com.mapleland.chatservice.application.auth;

import com.mapleland.chatservice.presentation.auth.TokenResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshAuthService {
    private final AuthDomainService authDomainService;

    public TokenResponseDto execute(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        return authDomainService.refreshAuth(refreshToken, request, response);
    }
}
