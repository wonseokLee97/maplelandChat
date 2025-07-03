package com.mapleland.chatService.application.auth;

import com.mapleland.chatService.application.redis.RedisService;
import com.mapleland.chatService.domain.auth.exception.AuthException;
import com.mapleland.chatService.presentation.auth.TokenResponseDto;
import com.mapleland.chatService.presentation.auth.dto.RenameRequestDto;
import com.mapleland.chatService.shared.jwt.JwtProvider;
import com.mapleland.chatService.shared.jwt.TokenClaims;
import com.mapleland.chatService.shared.utils.BadWordFilter;
import com.mapleland.chatService.shared.utils.HashUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthDomainService {
    private final RedisService redisService;
    private final JwtProvider jwtProvider;
    private final BadWordFilter badWordFilter;

    public TokenResponseDto initAuth(HttpServletRequest request, HttpServletResponse response) {
        try {
            return issueTokens(null, null, request, response);
        } catch (UnknownHostException e) {
            throw new AuthException.UnknownHostException("initAuth Failed: IP 추출 실패");
        }
    }

    public TokenResponseDto refreshAuth(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        TokenClaims claims = jwtProvider.validateToken(refreshToken);

        String userName = claims.getUserName();
        String imgPath = claims.getImgPath();

        try {
            return issueTokens(userName, imgPath, request, response);
        } catch (UnknownHostException e) {
            throw new AuthException.UnknownHostException("refreshAuth Failed: IP 추출 실패");
        }
    }

    public TokenResponseDto renameAuth(String refreshToken, RenameRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        TokenClaims claims = jwtProvider.validateToken(refreshToken);
        String UUID = HashUtil.sha256(claims.getId());
        String blockKey = "rename:block:" + UUID;

        // "rename:block"이 Redis 에 등록되어 있으면 닉네임 변경 X
        if (redisService.get(blockKey) != null)
            throw new AuthException.RenameRateLimitException("닉네임은 10분마다 변경할 수 있습니다.");

        // "rename:block" Duration 설정
        redisService.set(blockKey, "1", Duration.ofMinutes(10));

        String imgPath = claims.getImgPath();
        String rename = requestDto.getUserName();
        rename = badWordFilter.filter(rename);
        requestDto.setUserName(rename);

        try {
            return issueTokens(requestDto.getUserName(), imgPath, request, response);
        } catch (UnknownHostException e) {
            throw new AuthException.UnknownHostException("renameAuth Failed: IP 추출 실패");
        }
    }

    private TokenResponseDto issueTokens(String userName, String imgPath, HttpServletRequest request, HttpServletResponse response) throws UnknownHostException {
        String AccessToken = jwtProvider.generateAccessToken(userName, imgPath, request);
        String RefreshToken = jwtProvider.generateRefreshToken(userName, imgPath, request);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", RefreshToken)
                .httpOnly(true)
                .secure(false)
//                .sameSite("None")
                .path("/api/v1/auth")
                .maxAge(Duration.ofDays(7))
                .build();
                ;

        response.addHeader("Set-Cookie", cookie.toString());

        return TokenResponseDto.builder()
                .accessToken(AccessToken)
                .build();
    }
}
