package com.mapleland.chatservice.shared.config;

import com.mapleland.chatservice.shared.jwt.JwtChannelInterceptor;
import com.mapleland.chatservice.shared.jwt.JwtProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtProvider jwtProvider;

    @PostConstruct
    public void logInit() {
        System.out.println("WebSocketConfig initialized");
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 주소: ws://localhost:8080/chat
        // sockJS 클라이언트가 WebSocket HandShake를 하기 위해 연결할 endPoint를 지정
        registry.addEndpoint("/connect")
                .setAllowedOriginPatterns(
                        "http://127.0.0.1:5500",
                        "http://localhost:5500",
                        "http://localhost:5173",
                        "https://mapleland.gg",
                        "chrome-extension://*"
                )
                .withSockJS() // 웹 소켓을 지원하지 않는 브라우저는 sockJS를 사용
        ;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // MessageBroker 가 해당 "/sub" api를 구독하고 있는 클라이언트에게 메시지를 전달한다.
        // heart.beat 설정 25000ms -> 25s
        registry.enableSimpleBroker("/sub", "/queue")
                .setTaskScheduler(taskScheduler())
                .setHeartbeatValue(new long[] {25000, 25000});

        // 클라이언트로 부터 메시지를 받을 api 의 prefix 를 설정한다. "/pub/~~"
        registry.setApplicationDestinationPrefixes("/pub");
    }

    // JwtChannelInterceptor 실행
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new JwtChannelInterceptor(jwtProvider));
    }

    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.initialize();
        return taskScheduler;
    }
}
