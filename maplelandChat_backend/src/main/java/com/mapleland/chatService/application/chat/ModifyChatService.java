package com.mapleland.chatService.application.chat;

import com.mapleland.chatService.presentation.chat.dto.ChatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModifyChatService {
    private final ChatDomainService chatDomainService;

    public ChatResponseDto execute(String id) {
        return chatDomainService.modifyChat(id);
    }
}
