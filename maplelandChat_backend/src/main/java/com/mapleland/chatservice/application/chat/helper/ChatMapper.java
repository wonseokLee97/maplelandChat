package com.mapleland.chatservice.application.chat.helper;

import com.mapleland.chatservice.domain.chat.Chat;
import com.mapleland.chatservice.presentation.websocket.dto.ChatRequestDto;
import com.mapleland.chatservice.presentation.chat.dto.ChatResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    Chat toEntity(ChatRequestDto requestDto);
    ChatResponseDto toResponseDto(Chat chat);
}
