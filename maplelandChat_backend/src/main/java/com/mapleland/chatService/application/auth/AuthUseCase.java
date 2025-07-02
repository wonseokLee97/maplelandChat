package com.mapleland.chatService.application.auth;

import com.mapleland.chatService.presentation.auth.TokenResponseDto;
import com.mapleland.chatService.presentation.auth.dto.RenameRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUseCase {
    private final InitAuthService initAuthService;
    private final RefreshAuthService refreshAuthService;
    private final RenameAuthService renameAuthService;

    public TokenResponseDto initAuth(HttpServletRequest request, HttpServletResponse response) {
        return initAuthService.execute(request, response);
    }

    public TokenResponseDto refreshAuth(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        return refreshAuthService.execute(refreshToken, request, response);
    }

    public TokenResponseDto renameAuth(String refreshToken, RenameRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        return renameAuthService.execute(refreshToken, requestDto, request, response);
    }
}
