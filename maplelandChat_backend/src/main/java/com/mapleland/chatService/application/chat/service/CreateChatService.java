package com.mapleland.chatService.application.chat.service;

import com.mapleland.chatService.presentation.websocket.dto.ChatRequestDto;
import com.mapleland.chatService.presentation.chat.dto.ChatResponseDto;
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
