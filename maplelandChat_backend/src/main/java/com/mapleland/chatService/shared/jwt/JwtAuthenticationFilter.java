package com.mapleland.chatService.shared.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapleland.chatService.shared.api.ApiResponse;
import com.mapleland.chatService.shared.type.http.HttpErrorType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // 해당 Path 는 validate X
    private static final List<SecuredPath> UNSECURED_API_PATHS = List.of(
            new SecuredPath(HttpMethod.GET, "/api/v1/auth/init"),
            new SecuredPath(HttpMethod.GET, "/api/v1/auth/refresh"),
            new SecuredPath(HttpMethod.GET, "/api/v1/message")
    );

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    /**
     * HTTP 요청을 필터링하여 JWT 토큰을 확인하고, 토큰이 유효하면 인증을 수행
     * 토큰이 없거나 만료된 경우 새로운 토큰을 발급
     *
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param filterChain 필터 체인
     * @throws ServletException 서블릿 예외
     * @throws IOException 입출력 예외
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = resolveJwtToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
        } else {
            // 토큰 검증
            try {
                JwtAuthenticationToken authRequest = new JwtAuthenticationToken(token);
                // jwtAuthenticationProvider 로 위임
                Authentication authentication = authenticationManager.authenticate(authRequest);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(request, response);
            } catch (JwtException.ExpiredJwtException e) {
                // 토큰 재발급이 필요한 경우
                handleExpiredToken(response);
            } catch (JwtException.MalformedJwtException e) {
                // 토큰에 공백이나 규격 외 문자가 있는 경우
                handleMalformedToken(response);
            } catch (JwtException.IllegalArgumentException e) {
                handleInvalidToken(response);
            }
        }
    }


    /**
     * 만료된 토큰에 대해 새 토큰을 발급하여 응답하는 메서드, refresh 기반으로 다시 요청하도록..
     */
    private void handleExpiredToken(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        objectMapper.writeValue(response.getWriter(), ApiResponse.error(
                "토큰이 만료되거나 존재하지 않습니다. Refresh 요청 보내주세요",
                HttpErrorType.EXPIRED_TOKEN));
    }

    /**
     * 토큰에 공백이나 규격 외 문자가 추가된 경우
     */
    private void handleMalformedToken(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        objectMapper.writeValue(response.getWriter(), ApiResponse.error(
                "토큰에 공백이나 규격 외 문자가 추가되었습니다. 형식을 확인해주세요",
                HttpErrorType.MALFORMED_TOKEN));
    }

    /**
     * JWT 토큰이 인증되지 않은 경우 (토큰이 없는 경우)
     */
    private void handleInvalidToken(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        objectMapper.writeValue(response.getWriter(), ApiResponse.error(
                "인증받지 않은 사용자입니다.",
                HttpErrorType.INVALID_TOKEN));
    }

    /**
     * HTTP 요청에서 JWT 토큰을 추출하는 메서드
     */
    private String resolveJwtToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(JwtProperties.HEADER_STRING);
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return authorizationHeader.substring(JwtProperties.TOKEN_PREFIX.length());
        }

        return null;
    }

    /**
     * SECURED_API_PATHS 에 정의된 경로는 제외한다.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        // 필터 적용 대상만 필터를 타도록 (shouldNotFilter = true)
        boolean matches = UNSECURED_API_PATHS.stream()
                .anyMatch(sp -> sp.matches(method, path));

        return matches;
    }

    private static class SecuredPath {
        private final HttpMethod method;
        private final String pattern;
        private final AntPathMatcher matcher = new AntPathMatcher();

        public SecuredPath(HttpMethod method, String pattern) {
            this.method = method;
            this.pattern = pattern;
        }

        public boolean matches(HttpMethod reqMethod, String path) {
            return this.method == reqMethod && matcher.match(pattern, path);
        }
    }
}