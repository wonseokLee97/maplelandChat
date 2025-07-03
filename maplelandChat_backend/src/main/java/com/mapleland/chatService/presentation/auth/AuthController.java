package com.mapleland.chatService.presentation.auth;

import com.mapleland.chatService.application.auth.AuthUseCase;
import com.mapleland.chatService.presentation.auth.dto.RenameRequestDto;
import com.mapleland.chatService.shared.api.ApiResponse;
import com.mapleland.chatService.shared.type.http.HttpSuccessType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @GetMapping("/init")
    public ApiResponse<?> init(HttpServletRequest request,
                               HttpServletResponse response) {
        return ApiResponse.success(
                authUseCase.initAuth(request, response),
                HttpSuccessType.SUCCESS_TOKEN_ISSUANCE
        );
    }

    @GetMapping("/refresh")
    public ApiResponse<?> refreshToken(@CookieValue("refreshToken") String refreshToken,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        return ApiResponse.success(
                authUseCase.refreshAuth(refreshToken, request, response),
                HttpSuccessType.SUCCESS_TOKEN_REFRESH);
    }

    @PostMapping("/rename")
    public ApiResponse<?> renameToken(@CookieValue("refreshToken") String refreshToken,
                                      @RequestBody RenameRequestDto requestDto,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        TokenResponseDto tokenResponseDto = authUseCase.renameAuth(refreshToken, requestDto, request, response);

        return ApiResponse.success(
                tokenResponseDto,
                HttpSuccessType.SUCCESS_TOKEN_REFRESH);
    }
}
