package com.mapleland.chatservice.application.chat.service;

import com.mapleland.chatservice.presentation.websocket.dto.EntryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DecreaseChatEntryService {
    private final ChatDomainService chatDomainService;

    public EntryResponseDto execute(String itemId, String UUID) {
        return chatDomainService.decreaseEntry(itemId, UUID);
    }
}
