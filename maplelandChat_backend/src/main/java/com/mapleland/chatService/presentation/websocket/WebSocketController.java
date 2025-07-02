package com.mapleland.chatService.presentation.websocket;

import com.mapleland.chatService.application.chat.ChatUseCase;
import com.mapleland.chatService.presentation.websocket.dto.ChatRequestDto;
import com.mapleland.chatService.presentation.websocket.dto.EntryRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final ChatUseCase chatUseCase;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void createChat(@Valid ChatRequestDto requestDto, Message<?> message) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String UUID = (String) Objects.requireNonNull(accessor.getSessionAttributes()).get("UUID");

        messagingTemplate.convertAndSend("/sub/channel/" + requestDto.getItemId(),
                chatUseCase.createChat(requestDto, UUID));
    }

    @MessageMapping("/entry")
    public void increaseEntry(EntryRequestDto requestDto, Message<?> message) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String UUID = (String) Objects.requireNonNull(accessor.getSessionAttributes()).get("UUID");

        messagingTemplate.convertAndSend("/sub/entry/" + requestDto.getItemId(),
                chatUseCase.increaseEntry(requestDto, UUID));
    }
}
