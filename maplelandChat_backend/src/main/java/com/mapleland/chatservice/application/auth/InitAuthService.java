package com.mapleland.chatservice.application.auth;

import com.mapleland.chatservice.presentation.auth.TokenResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitAuthService {
    private final AuthDomainService authDomainService;

    public TokenResponseDto execute(HttpServletRequest request, HttpServletResponse response) {
        return authDomainService.initAuth(request, response);
    }
}
