package com.mapleland.chatService.application.chat;

import com.mapleland.chatService.domain.chat.Chat;
import com.mapleland.chatService.infrastructure.chat.ChatRepository;
import com.mapleland.chatService.presentation.chat.dto.ChatResponseDto;
import com.mapleland.chatService.presentation.chat.dto.ReportChatRequestDto;
import com.mapleland.chatService.presentation.websocket.dto.ChatRequestDto;
import com.mapleland.chatService.presentation.websocket.dto.EntryRequestDto;
import com.mapleland.chatService.presentation.websocket.dto.EntryResponseDto;
import com.mapleland.chatService.shared.utils.BadWordFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatDomainService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final ChatPolicyHelper spamPolicy;
    private final ChatEntryHelper entryHelper;
    private final BadWordFilter badWordFilter;

    public ChatResponseDto createChat(ChatRequestDto requestDto, String UUID) {
        // 도배 방지
        spamPolicy.checkSpamAndBlock(UUID);
        spamPolicy.checkRepeatedMessage(requestDto, UUID);
        spamPolicy.checkIsBanned(UUID);
        requestDto.setUuid(UUID);

        String message = requestDto.getMessage();
        message = badWordFilter.filter(message);
        requestDto.setMessage(message);

        Chat chat = chatMapper.toEntity(requestDto);
        Chat save = chatRepository.save(chat);

        return chatMapper.toResponseDto(save);
    }

    public List<ChatResponseDto> getChat(String itemId, int limit, String cursor) {
        List<Chat> chatList = chatRepository.getChatsByCursor(itemId, limit, cursor);
        List<ChatResponseDto> responseDtoList = new ArrayList<>();

        for (Chat chat : chatList) {
            responseDtoList.add(chatMapper.toResponseDto(chat));
        }

        return responseDtoList;
    }

    public ChatResponseDto modifyChat(String id) {
        Chat chat = chatRepository.modifyChat(id);
        return chatMapper.toResponseDto(chat);
    }

    public void reportChat(ReportChatRequestDto requestDto) {
        spamPolicy.checkReportAndBan(requestDto);
    }

    public EntryResponseDto increaseEntry(EntryRequestDto entryRequestDto, String uuid) {
        return entryHelper.increase(entryRequestDto, uuid);
    }

    public EntryResponseDto decreaseEntry(String itemId, String uuid) {
        return entryHelper.decrease(itemId, uuid);
    }
}
