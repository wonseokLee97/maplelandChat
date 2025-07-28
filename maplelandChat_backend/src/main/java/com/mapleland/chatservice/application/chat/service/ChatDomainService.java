package com.mapleland.chatservice.application.chat.service;

import com.mapleland.chatservice.application.chat.helper.ChatEntryHelper;
import com.mapleland.chatservice.application.chat.helper.ChatMapper;
import com.mapleland.chatservice.application.chat.helper.ChatPolicyHelper;
import com.mapleland.chatservice.application.chat.helper.XssSanitizerHelper;
import com.mapleland.chatservice.domain.chat.Chat;
import com.mapleland.chatservice.infrastructure.chat.ChatRepository;
import com.mapleland.chatservice.presentation.chat.dto.ChatResponseDto;
import com.mapleland.chatservice.presentation.chat.dto.ReportChatRequestDto;
import com.mapleland.chatservice.presentation.websocket.dto.*;
import com.mapleland.chatservice.shared.utils.BadWordFilter;
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
    private final XssSanitizerHelper xssSanitizerHelper;

    public ChatResponseDto createChat(ChatRequestDto requestDto, String UUID) {
        // 도배 방지
        spamPolicy.checkSpamAndBlock(UUID);
        spamPolicy.checkRepeatedMessage(requestDto, UUID);
        spamPolicy.checkIsBanned(UUID);
        requestDto.setUuid(UUID);

        String message = requestDto.getMessage();
        message = badWordFilter.filter(message);
        message = xssSanitizerHelper.sanitize(message);
        requestDto.setMessage(message);

        Chat chat = chatMapper.toEntity(requestDto);
        Chat save = chatRepository.save(chat);

        return chatMapper.toResponseDto(save);
    }

    public List<ChatResponseDto> getChat(String itemId, int limit, String cursor, String search) {
        List<Chat> chatList = chatRepository.getChatsByCursor(itemId, limit, cursor, search);
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
