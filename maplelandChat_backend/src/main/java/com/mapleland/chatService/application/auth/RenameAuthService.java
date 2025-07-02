package com.mapleland.chatService.application.auth;

import com.mapleland.chatService.presentation.auth.TokenResponseDto;
import com.mapleland.chatService.presentation.auth.dto.RenameRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RenameAuthService {
    private final AuthDomainService authDomainService;

    public TokenResponseDto execute(String refreshToken, RenameRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        return authDomainService.renameAuth(refreshToken, requestDto, request, response);
    }
}
