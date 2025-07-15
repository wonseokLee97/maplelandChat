package com.mapleland.chatservice.application.chat.service;

import com.mapleland.chatservice.presentation.websocket.dto.ChatRequestDto;
import com.mapleland.chatservice.presentation.chat.dto.ChatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateChatService {
    private final ChatDomainService chatDomainService;

    public ChatResponseDto execute(ChatRequestDto chatRequestDto, String UUID) {
        return chatDomainService.createChat(chatRequestDto, UUID);
    }
}
