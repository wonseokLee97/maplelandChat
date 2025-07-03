package com.mapleland.chatService.application.chat;

import com.mapleland.chatService.domain.chat.Chat;
import com.mapleland.chatService.presentation.websocket.dto.ChatRequestDto;
import com.mapleland.chatService.presentation.chat.dto.ChatResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    Chat toEntity(ChatRequestDto requestDto);
    ChatResponseDto toResponseDto(Chat chat);
}
