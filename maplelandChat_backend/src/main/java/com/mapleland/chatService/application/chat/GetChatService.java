package com.mapleland.chatService.application.chat;

import com.mapleland.chatService.presentation.chat.dto.ChatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetChatService {
    private final ChatDomainService chatDomainService;

    public List<ChatResponseDto> execute(String itemId, int limit, String cursor) {
        return chatDomainService.getChat(itemId, limit, cursor);
    }
}
