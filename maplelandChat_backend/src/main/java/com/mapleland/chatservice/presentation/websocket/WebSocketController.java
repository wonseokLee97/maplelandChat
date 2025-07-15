package com.mapleland.chatservice.presentation.websocket;

import com.mapleland.chatservice.application.alert.AlertUseCase;
import com.mapleland.chatservice.application.chat.ChatUseCase;
import com.mapleland.chatservice.presentation.websocket.dto.AlertRequestDto;
import com.mapleland.chatservice.presentation.websocket.dto.ChatRequestDto;
import com.mapleland.chatservice.presentation.websocket.dto.EntryRequestDto;
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
    private final AlertUseCase alertUseCase;
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

    @MessageMapping("/alert")
    public void createAlert(AlertRequestDto requestDto) {
        messagingTemplate.convertAndSend("/sub/alert/" + requestDto.getTargetUuid(),
                alertUseCase.createAlert(requestDto));
    }
}
