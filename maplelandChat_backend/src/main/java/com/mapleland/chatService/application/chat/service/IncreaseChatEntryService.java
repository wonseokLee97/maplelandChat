package com.mapleland.chatService.application.chat.service;

import com.mapleland.chatService.presentation.websocket.dto.EntryRequestDto;
import com.mapleland.chatService.presentation.websocket.dto.EntryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncreaseChatEntryService {
    private final ChatDomainService chatDomainService;

    public EntryResponseDto execute(EntryRequestDto entryRequestDto, String UUID) {
        return chatDomainService.increaseEntry(entryRequestDto, UUID);
    }
}
