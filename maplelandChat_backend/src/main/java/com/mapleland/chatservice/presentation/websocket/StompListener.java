package com.mapleland.chatservice.presentation.websocket;

import com.mapleland.chatservice.application.chat.ChatUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class StompListener {

    private final ChatUseCase chatUseCase;
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener(SessionSubscribeEvent.class)
    public void handleSessionSubscribe(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String destination = accessor.getDestination();
        String subscriptionId = accessor.getSubscriptionId();

        // entry 채널 구독일 때만 플래그 저장
        if (destination != null && destination.startsWith("/sub/entry/")) {
            Objects.requireNonNull(accessor.getSessionAttributes()).put("isEntrySubscription_" + subscriptionId, true);
        }
    }


    // 세션 종료 이후..
    @EventListener(SessionDisconnectEvent.class)
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String UUID = (String) Objects.requireNonNull(accessor.getSessionAttributes()).get("UUID");
        String itemId = (String) Objects.requireNonNull(accessor.getSessionAttributes()).get("itemId");

        messagingTemplate.convertAndSend("/sub/entry/" + itemId,
                chatUseCase.decreaseEntry(itemId, UUID));
    }

    // 구독 해제 이후..
    @EventListener(SessionUnsubscribeEvent.class)
    public void handleSessionUnsubscribe(SessionUnsubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String subscriptionId = accessor.getSubscriptionId();

        // entry 관련 구독만 처리
        if (Boolean.TRUE.equals(Objects.requireNonNull(accessor.getSessionAttributes()).get("isEntrySubscription_" + subscriptionId))) {
            String UUID = (String) accessor.getSessionAttributes().get("UUID");
            String itemId = (String) accessor.getSessionAttributes().get("itemId");

            messagingTemplate.convertAndSend("/sub/entry/" + itemId,
                    chatUseCase.decreaseEntry(itemId, UUID));
        }
    }
}


