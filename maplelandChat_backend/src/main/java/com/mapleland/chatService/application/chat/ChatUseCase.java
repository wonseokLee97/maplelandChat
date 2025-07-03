package com.mapleland.chatService.application.chat;

import com.mapleland.chatService.presentation.chat.dto.*;
import com.mapleland.chatService.presentation.websocket.dto.ChatRequestDto;
import com.mapleland.chatService.presentation.websocket.dto.EntryRequestDto;
import com.mapleland.chatService.presentation.websocket.dto.EntryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatUseCase {
    private final CreateChatService createChatService;
    private final GetChatService getChatService;
    private final ReportChatService reportChatService;
    private final IncreaseChatEntryService increaseChatEntryService;
    private final DecreaseChatEntryService decreaseChatEntryService;

    public ChatResponseDto createChat(ChatRequestDto chatRequestDto, String UUID) {
        return createChatService.execute(chatRequestDto, UUID);
    }

    public List<ChatResponseDto> getChat(String itemId, int limit, String cursor) {
        return getChatService.execute(itemId, limit, cursor);
    }

    public EntryResponseDto increaseEntry(EntryRequestDto entryRequestDto, String UUID) {
        return increaseChatEntryService.execute(entryRequestDto, UUID);
    }

    public EntryResponseDto decreaseEntry(String itemId, String UUID) {
        return decreaseChatEntryService.execute(itemId, UUID);
    }

    public void reportChat(ReportChatRequestDto requestDto) {
        reportChatService.execute(requestDto);
    }
}
