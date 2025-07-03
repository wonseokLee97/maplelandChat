package com.mapleland.chatService.shared.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

/**
 * WebSocket 연결을 처리하며, JWT 토큰 검증을 수행하는 인터셉터 클래스
 */
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Slf4j
public class JwtChannelInterceptor implements ChannelInterceptor {
    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // CONNECT 요청의 경우
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // 헤더에서 JWT 토큰 추출
            String token = accessor.getFirstNativeHeader("Authorization");

            // JWT 토큰 검증
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);

                try {
                    TokenClaims claims = jwtProvider.validateToken(token);
                    // WebSocket 세션에 사용자 정보 설정
                    accessor.getSessionAttributes().put("UUID", claims.getId());
                    accessor.getSessionAttributes().put("userName", claims.getUserName());
                } catch (JwtException.ExpiredJwtException e) {
                    throw new MessageDeliveryException("EXPIRED_TOKEN");
                }
            } else {
                throw new MessageDeliveryException("Authorization header is missing or invalid");
            }
        }
        // SUBSCRIBE 요청의 경우 (`/sub/entry/${itemId}`)
        else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            String itemId = accessor.getFirstNativeHeader("itemId");

            if (itemId != null) {
                accessor.getSessionAttributes().put("itemId", itemId);
            }
        }



        return message;
    }
}
