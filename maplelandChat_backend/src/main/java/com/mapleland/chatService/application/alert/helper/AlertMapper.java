package com.mapleland.chatService.application.alert.helper;

import com.mapleland.chatService.domain.alert.Alert;
import com.mapleland.chatService.presentation.websocket.dto.AlertRequestDto;
import com.mapleland.chatService.presentation.websocket.dto.AlertResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlertMapper {
    Alert toEntity(AlertRequestDto requestDto);
    AlertResponseDto toResponseDto(Alert alert);
}
