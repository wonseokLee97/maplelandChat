package com.mapleland.chatService.application.auth;

import com.mapleland.chatService.presentation.auth.TokenResponseDto;
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
